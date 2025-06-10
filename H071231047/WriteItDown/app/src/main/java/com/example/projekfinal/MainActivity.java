package com.example.projekfinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity implements NoteAdapter.OnItemClickListener {

    private Toolbar toolbarMain;
    private RecyclerView recyclerViewNotes;
    private FloatingActionButton fabAddNote;
    private TextView textViewNoData;
    private EditText editTextSearch;
    private ImageButton buttonClearSearch;
    private LinearLayout searchBarLayout;
    private FrameLayout fragmentContainer;
    private BottomNavigationView bottomNavigationView;

    // Category filter related components
    private Spinner spinnerCategories;
    private String currentCategory = "All";
    private LinearLayout categoryFilterLayout;

    // ZenQuotes API related components
    private MaterialCardView quoteCardView;
    private TextView textViewQuote;
    private TextView textViewAuthor;
    private ImageButton buttonRefreshQuote;
    private TextView textViewError;
    private QuoteApiClient quoteApiClient;
    private boolean isQuoteLoading = false;

    private DatabaseHelper dbHelper;
    private List<Note> noteList;
    private NoteAdapter noteAdapter;

    private static final String THEME_PREFS = "theme_prefs";
    private static final String THEME_KEY = "theme_key";
    private static final String NAV_PREFS = "nav_prefs";
    private static final String LAST_TAB = "last_tab";

    // Flag to track theme change in progress
    private boolean isThemeChanging = false;

    private ActivityResultLauncher<Intent> addEditNoteLauncher;

    // Handler for delayed operations
    private final Handler uiHandler = new Handler(Looper.getMainLooper());

    // Biometric related fields
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Apply theme before setContentView
        applyTheme();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupRecyclerView();
        setupActivityLauncher();
        setupClickListeners();
        setupSearchFunctionality();
        setupBottomNavigation();
        setupCategorySpinner();
        setupBiometricAuthentication();
        preventKeyboardFromShowing();

        // Immediately check if we're restoring after theme change
        SharedPreferences themePrefs = getSharedPreferences(THEME_PREFS, Context.MODE_PRIVATE);
        boolean fromSettings = themePrefs.getBoolean("theme_changed_from_settings", false);

        // If theme was changed from settings, ensure quote is hidden
        if (fromSettings && quoteCardView != null) {
            quoteCardView.setVisibility(View.GONE);
            // Reset the flag
            themePrefs.edit().putBoolean("theme_changed_from_settings", false).apply();
        }

        // Special case: intentionally delay the quote fetch to avoid flashing
        if (!fromSettings) {
            fetchRandomQuote();
        }
    }

    private void setupBiometricAuthentication() {
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(MainActivity.this, executor,
                new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode, CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                        Toast.makeText(getApplicationContext(),
                                "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
                        // Reset navigation to home tab on error
                        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
                    }

                    @Override
                    public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        // Proceed to favorites screen after successful authentication
                        displayFavoritesScreenAfterAuth();
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        Toast.makeText(getApplicationContext(),
                                "Authentication failed", Toast.LENGTH_SHORT).show();
                        // Reset navigation to home tab on failure
                        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
                    }
                });

        // Create the BiometricPrompt
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle(getString(R.string.biometric_title))
                .setSubtitle(getString(R.string.biometric_subtitle))
                .setNegativeButtonText(getString(R.string.cancel))
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_WEAK)
                .build();
    }

    private boolean isBiometricAvailable() {
        BiometricManager biometricManager = BiometricManager.from(this);
        int result = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK);
        return result == BiometricManager.BIOMETRIC_SUCCESS;
    }

    private void authenticateWithBiometric() {
        biometricPrompt.authenticate(promptInfo);
    }

    private void displayFavoritesScreenAfterAuth() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.title_favorites));
        }

        clearFragmentManager();
        ensureFavoritesVisibility();
    }

    private void initializeViews() {
        toolbarMain = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbarMain);

        recyclerViewNotes = findViewById(R.id.recyclerViewNotes);
        fabAddNote = findViewById(R.id.fab_add_note);
        textViewNoData = findViewById(R.id.textViewNoData);
        editTextSearch = findViewById(R.id.editTextSearch);
        buttonClearSearch = findViewById(R.id.buttonClearSearch);
        searchBarLayout = findViewById(R.id.search_bar_layout);
        fragmentContainer = findViewById(R.id.fragment_container);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Initialize category filter components
        categoryFilterLayout = findViewById(R.id.category_filter_layout);
        spinnerCategories = findViewById(R.id.spinnerCategories);

        // Initialize ZenQuotes API related views
        quoteCardView = findViewById(R.id.quoteCardView);
        textViewQuote = findViewById(R.id.textViewQuote);
        textViewAuthor = findViewById(R.id.textViewAuthor);
        buttonRefreshQuote = findViewById(R.id.buttonRefreshQuote);
        textViewError = findViewById(R.id.textViewError);

        // Initialize the quote API client
        quoteApiClient = new QuoteApiClient();
    }

    private void setupCategorySpinner() {
        List<String> categories = new ArrayList<>();
        categories.add("All"); // Default option to show all notes
        categories.addAll(dbHelper.getAllCategories());

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCategories.setAdapter(categoryAdapter);
        spinnerCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentCategory = parent.getItemAtPosition(position).toString();
                loadNotes();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                currentCategory = "All";
                loadNotes();
            }
        });
    }

    private void setupRecyclerView() {
        dbHelper = new DatabaseHelper(this);
        noteList = new ArrayList<>();
        noteAdapter = new NoteAdapter(this, noteList, this);
        recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNotes.setAdapter(noteAdapter);
    }

    private void setupActivityLauncher() {
        addEditNoteLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    loadNotes();
                    // Refresh category spinner as new categories might have been added
                    setupCategorySpinner();
                }
        );
    }

    private void setupClickListeners() {
        fabAddNote.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
            addEditNoteLauncher.launch(intent);
        });

        buttonClearSearch.setOnClickListener(v -> {
            editTextSearch.setText("");
            buttonClearSearch.setVisibility(View.GONE);
            preventKeyboardFromShowing();
        });

        buttonRefreshQuote.setOnClickListener(v -> fetchRandomQuote());
    }

    private void setupSearchFunctionality() {
        editTextSearch.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && editTextSearch.getText().toString().isEmpty()) {
                buttonClearSearch.setVisibility(View.GONE);
            } else if (hasFocus) {
                buttonClearSearch.setVisibility(View.VISIBLE);
            }
        });

        editTextSearch.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchNotes(s.toString());
                buttonClearSearch.setVisibility(s.length() > 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {
            }
        });
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            // Immediately hide quote card when leaving home screen
            if (itemId != R.id.navigation_home && quoteCardView != null) {
                quoteCardView.setVisibility(View.GONE);
            }

            if (itemId == R.id.navigation_home) {
                showHomeScreen();
                saveLastTab(itemId);
                return true;
            } else if (itemId == R.id.navigation_favorites) {
                // Check for biometric authentication before showing favorites
                if (isBiometricAvailable()) {
                    authenticateWithBiometric();
                } else {
                    Toast.makeText(this, getString(R.string.biometric_not_available),
                            Toast.LENGTH_SHORT).show();
                    // Fallback to showing favorites if biometrics not available
                    showFavoritesScreen();
                }
                saveLastTab(itemId);
                return true;
            } else if (itemId == R.id.navigation_settings) {
                showSettingsScreen();
                saveLastTab(itemId);
                return true;
            }
            return false;
        });

        // Get the last active tab that survived theme change
        SharedPreferences themePrefs = getSharedPreferences(THEME_PREFS, Context.MODE_PRIVATE);
        SharedPreferences navPrefs = getSharedPreferences(NAV_PREFS, Context.MODE_PRIVATE);

        // Use theme change preference as priority
        int lastTabFromTheme = themePrefs.getInt("last_active_tab", -1);
        int lastTab = lastTabFromTheme != -1 ?
                lastTabFromTheme :
                navPrefs.getInt(LAST_TAB, R.id.navigation_home);

        bottomNavigationView.setSelectedItemId(lastTab);
    }

    private void saveLastTab(int tabId) {
        getSharedPreferences(NAV_PREFS, Context.MODE_PRIVATE)
                .edit()
                .putInt(LAST_TAB, tabId)
                .apply();
    }

    private void applyTheme() {
        SharedPreferences themePrefs = getSharedPreferences(THEME_PREFS, Context.MODE_PRIVATE);
        int savedTheme = themePrefs.getInt(THEME_KEY, 3); // Default to system mode

        switch (savedTheme) {
            case 1: // Light Mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case 2: // Dark Mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case 3: // System Mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
        }
    }

    // Fixed theme switching method that prevents quote box from appearing over settings
    public void setAppTheme(int themeMode) {
        // Save theme preference
        SharedPreferences themePrefs = getSharedPreferences(THEME_PREFS, Context.MODE_PRIVATE);
        themePrefs.edit().putInt(THEME_KEY, themeMode).apply();

        // Store current screen state
        int currentTabId = bottomNavigationView.getSelectedItemId();
        themePrefs.edit().putInt("last_active_tab", currentTabId).apply();

        // 1. Force hide the quote card immediately
        if (quoteCardView != null) {
            quoteCardView.setVisibility(View.GONE);
        }

        // 2. Set a flag in preferences that we're changing theme from settings
        if (currentTabId == R.id.navigation_settings) {
            themePrefs.edit().putBoolean("theme_changed_from_settings", true).apply();
        } else {
            themePrefs.edit().putBoolean("theme_changed_from_settings", false).apply();
        }

        // 3. Apply the theme change
        switch (themeMode) {
            case 1: // Light Mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            case 2: // Dark Mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case 3: // System Mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                // Apply real system setting after a brief delay to avoid flash
                new Handler(Looper.getMainLooper()).postDelayed(() ->
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM), 50);
                break;
        }
    }

    // Enhanced reset methods for more reliable UI state after theme change
    private void resetHomeUI() {
        // First ensure all views have correct visibility
        if (fragmentContainer != null) fragmentContainer.setVisibility(View.GONE);
        if (searchBarLayout != null) searchBarLayout.setVisibility(View.VISIBLE);
        if (categoryFilterLayout != null) categoryFilterLayout.setVisibility(View.VISIBLE);
        if (recyclerViewNotes != null) recyclerViewNotes.setVisibility(View.VISIBLE);
        if (fabAddNote != null) fabAddNote.setVisibility(View.VISIBLE);

        // Handle quote card with special care
        if (quoteCardView != null) {
            // Force reset layout params to ensure consistent positioning
            quoteCardView.setTranslationX(0);
            quoteCardView.setTranslationY(0);
            quoteCardView.setScaleX(1.0f);
            quoteCardView.setScaleY(1.0f);
            quoteCardView.requestLayout();

            // Show quote card only after layout pass
            uiHandler.post(() -> {
                quoteCardView.setVisibility(View.VISIBLE);
                quoteCardView.invalidate();
            });
        }

        loadNotes();
    }

    private void resetSettingsUI() {
        if (searchBarLayout != null) searchBarLayout.setVisibility(View.GONE);
        if (categoryFilterLayout != null) categoryFilterLayout.setVisibility(View.GONE);
        if (recyclerViewNotes != null) recyclerViewNotes.setVisibility(View.GONE);
        if (fabAddNote != null) fabAddNote.setVisibility(View.GONE);
        if (textViewNoData != null) textViewNoData.setVisibility(View.GONE);
        if (quoteCardView != null) quoteCardView.setVisibility(View.GONE);

        if (fragmentContainer != null) {
            fragmentContainer.setVisibility(View.VISIBLE);
            loadFragment(new SettingsFragment());
        }
    }

    private void resetFavoritesUI() {
        if (searchBarLayout != null) searchBarLayout.setVisibility(View.GONE);
        if (categoryFilterLayout != null) categoryFilterLayout.setVisibility(View.GONE);
        if (recyclerViewNotes != null) recyclerViewNotes.setVisibility(View.GONE);
        if (fabAddNote != null) fabAddNote.setVisibility(View.GONE);
        if (textViewNoData != null) textViewNoData.setVisibility(View.GONE);
        if (quoteCardView != null) quoteCardView.setVisibility(View.GONE);

        if (fragmentContainer != null) {
            fragmentContainer.setVisibility(View.VISIBLE);
            loadFragment(new FavoritesFragment());
        }
    }

    // Original methods with calls to the enhanced reset methods
    private void ensureHomeVisibility() {
        if (!isThemeChanging) {
            if (fragmentContainer != null) fragmentContainer.setVisibility(View.GONE);
            if (searchBarLayout != null) searchBarLayout.setVisibility(View.VISIBLE);
            if (categoryFilterLayout != null) categoryFilterLayout.setVisibility(View.VISIBLE);
            if (recyclerViewNotes != null) recyclerViewNotes.setVisibility(View.VISIBLE);
            if (fabAddNote != null) fabAddNote.setVisibility(View.VISIBLE);
            if (quoteCardView != null) quoteCardView.setVisibility(View.VISIBLE);

            loadNotes();
        }
    }

    private void ensureSettingsVisibility() {
        if (!isThemeChanging) {
            if (searchBarLayout != null) searchBarLayout.setVisibility(View.GONE);
            if (categoryFilterLayout != null) categoryFilterLayout.setVisibility(View.GONE);
            if (recyclerViewNotes != null) recyclerViewNotes.setVisibility(View.GONE);
            if (fabAddNote != null) fabAddNote.setVisibility(View.GONE);
            if (textViewNoData != null) textViewNoData.setVisibility(View.GONE);
            if (quoteCardView != null) quoteCardView.setVisibility(View.GONE);

            if (fragmentContainer != null) {
                fragmentContainer.setVisibility(View.VISIBLE);
                loadFragment(new SettingsFragment());
            }
        }
    }

    private void ensureFavoritesVisibility() {
        if (!isThemeChanging) {
            if (searchBarLayout != null) searchBarLayout.setVisibility(View.GONE);
            if (categoryFilterLayout != null) categoryFilterLayout.setVisibility(View.GONE);
            if (recyclerViewNotes != null) recyclerViewNotes.setVisibility(View.GONE);
            if (fabAddNote != null) fabAddNote.setVisibility(View.GONE);
            if (textViewNoData != null) textViewNoData.setVisibility(View.GONE);
            if (quoteCardView != null) quoteCardView.setVisibility(View.GONE);

            if (fragmentContainer != null) {
                fragmentContainer.setVisibility(View.VISIBLE);
                loadFragment(new FavoritesFragment());
            }
        }
    }

    private void showHomeScreen() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.notes_toolbar_title));
        }

        clearFragmentManager();
        ensureHomeVisibility();

        if (textViewQuote.getText().toString().isEmpty() ||
                (textViewError != null && textViewError.getVisibility() == View.VISIBLE)) {
            fetchRandomQuote();
        }
    }

    private void showFavoritesScreen() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.title_favorites));
        }

        clearFragmentManager();
        ensureFavoritesVisibility();
    }

    private void showSettingsScreen() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.title_settings));
        }

        clearFragmentManager();
        ensureSettingsVisibility();
    }

    private void clearFragmentManager() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            for (int i = 0; i < getSupportFragmentManager().getBackStackEntryCount(); i++) {
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        preventKeyboardFromShowing();

        // Extra check to ensure quote visibility matches current tab
        int currentTabId = bottomNavigationView.getSelectedItemId();
        if (currentTabId != R.id.navigation_home && quoteCardView != null) {
            quoteCardView.setVisibility(View.GONE);
        }

        // Apply correct visibility based on current tab
        if (currentTabId == R.id.navigation_settings) {
            ensureSettingsVisibility();
        } else if (currentTabId == R.id.navigation_favorites) {
            ensureFavoritesVisibility();
        } else {
            ensureHomeVisibility();
        }
    }

    private void preventKeyboardFromShowing() {
        if (editTextSearch != null) {
            editTextSearch.clearFocus();
        }

        if (toolbarMain != null) {
            toolbarMain.requestFocus();
        } else {
            View decorView = getWindow().getDecorView();
            if (decorView != null) {
                decorView.requestFocus();
            }
        }

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View currentFocusedView = getCurrentFocus();
        if (imm != null) {
            if (currentFocusedView != null) {
                imm.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), 0);
            } else {
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
            }
        }
    }

    private void loadNotes() {
        String currentSearchQuery = editTextSearch.getText().toString().trim();

        if (currentCategory.equals("All")) {
            if (currentSearchQuery.isEmpty()) {
                noteList.clear();
                noteList.addAll(dbHelper.getAllNotes());
            } else {
                searchNotes(currentSearchQuery);
            }
        } else {
            noteList.clear();
            if (currentSearchQuery.isEmpty()) {
                noteList.addAll(dbHelper.getNotesByCategory(currentCategory));
            } else {
                // Get notes by category and filter by search query
                List<Note> categoryNotes = dbHelper.getNotesByCategory(currentCategory);
                for (Note note : categoryNotes) {
                    if (note.getTitle().toLowerCase().contains(currentSearchQuery.toLowerCase())) {
                        noteList.add(note);
                    }
                }
            }
        }

        noteAdapter.setNotes(noteList);
        checkIfNoData(currentSearchQuery);
    }

    private void searchNotes(String query) {
        noteList.clear();
        if (query.isEmpty()) {
            if (currentCategory.equals("All")) {
                noteList.addAll(dbHelper.getAllNotes());
            } else {
                noteList.addAll(dbHelper.getNotesByCategory(currentCategory));
            }
        } else {
            if (currentCategory.equals("All")) {
                noteList.addAll(dbHelper.searchNotesByTitle(query));
            } else {
                // Search within the selected category
                List<Note> categoryNotes = dbHelper.getNotesByCategory(currentCategory);
                for (Note note : categoryNotes) {
                    if (note.getTitle().toLowerCase().contains(query.toLowerCase())) {
                        noteList.add(note);
                    }
                }
            }
        }
        noteAdapter.setNotes(noteList);
        checkIfNoData(query);
    }

    private void checkIfNoData(String searchQuery) {
        if (noteList.isEmpty()) {
            recyclerViewNotes.setVisibility(View.GONE);
            textViewNoData.setVisibility(View.VISIBLE);

            if (searchQuery != null && !searchQuery.isEmpty()) {
                if (!currentCategory.equals("All")) {
                    textViewNoData.setText(String.format(getString(R.string.no_search_results_category), searchQuery, currentCategory));
                } else {
                    textViewNoData.setText(String.format(getString(R.string.no_search_results), searchQuery));
                }
            } else {
                if (!currentCategory.equals("All")) {
                    textViewNoData.setText(String.format(getString(R.string.no_notes_in_category), currentCategory));
                } else {
                    textViewNoData.setText(getString(R.string.no_data_available));
                }
            }
        } else {
            textViewNoData.setVisibility(View.GONE);
            recyclerViewNotes.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(Note note) {
        Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
        intent.putExtra("note_id", note.getId());
        addEditNoteLauncher.launch(intent);
    }

    private void fetchRandomQuote() {
        if (isQuoteLoading) return;

        isQuoteLoading = true;
        quoteCardView.setVisibility(View.VISIBLE);
        textViewQuote.setText("Loading quote...");
        textViewAuthor.setText("");

        if (textViewError != null) {
            textViewError.setVisibility(View.GONE);
        }

        buttonRefreshQuote.setEnabled(false);

        quoteApiClient.getRandomQuote(new QuoteApiClient.QuoteCallback() {
            @Override
            public void onSuccess(Quote quote) {
                runOnUiThread(() -> {
                    isQuoteLoading = false;
                    buttonRefreshQuote.setEnabled(true);

                    if (quote != null && quote.getText() != null) {
                        textViewQuote.setText(quote.getText());
                        textViewAuthor.setText("— " + (quote.getAuthor() != null ? quote.getAuthor() : "Unknown"));
                        quoteCardView.setVisibility(View.VISIBLE);

                        if (textViewError != null) {
                            textViewError.setVisibility(View.GONE);
                        }
                    } else {
                        textViewQuote.setText("");
                        textViewAuthor.setText("");

                        if (textViewError != null) {
                            textViewError.setText("Could not load quote content");
                            textViewError.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }

            @Override
            public void onError(String errorMessage) {
                runOnUiThread(() -> {
                    isQuoteLoading = false;
                    buttonRefreshQuote.setEnabled(true);

                    if (textViewError != null) {
                        textViewQuote.setText("");
                        textViewAuthor.setText("");
                        textViewError.setText("Failed to load quote. Tap refresh to try again.");
                        textViewError.setVisibility(View.VISIBLE);
                        quoteCardView.setVisibility(View.VISIBLE);
                    } else {
                        Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}