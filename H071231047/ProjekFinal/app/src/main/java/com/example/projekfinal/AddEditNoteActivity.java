package com.example.projekfinal;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddEditNoteActivity extends AppCompatActivity {

    private TextInputLayout textInputLayoutTitle;
    private TextInputLayout textInputLayoutContent;
    private EditText editTextTitle;
    private EditText editTextContent;
    private MaterialButton buttonSubmit;
    private TextView textViewCreatedDate;
    private TextView textViewUpdatedDate;
    private LinearLayout layoutTimestamps;
    private Toolbar toolbar;

    // Category components
    private AutoCompleteTextView autoCompleteCategory;
    private List<String> categories = new ArrayList<>();
    private ArrayAdapter<String> categoryAdapter;

    private DatabaseHelper dbHelper;
    private boolean isEditMode = false;
    private Note currentNote;
    private int noteId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_note);

        dbHelper = new DatabaseHelper(this);

        initViews();
        checkForEditMode();
        setupToolbar();
        setupSubmitButton();

        // Initialize category dropdown
        autoCompleteCategory = findViewById(R.id.autoCompleteCategory);
        loadCategories();
        setupCategoryDropdown();
    }

    private void initViews() {
        textInputLayoutTitle = findViewById(R.id.textInputLayoutTitle);
        textInputLayoutContent = findViewById(R.id.textInputLayoutContent);
        editTextTitle = textInputLayoutTitle.getEditText();
        editTextContent = textInputLayoutContent.getEditText();
        buttonSubmit = findViewById(R.id.buttonSubmit);
        layoutTimestamps = findViewById(R.id.layoutTimestamps);
        textViewCreatedDate = findViewById(R.id.textViewCreatedAt); // not textViewCreatedDate
        textViewUpdatedDate = findViewById(R.id.textViewUpdatedAt);
        toolbar = findViewById(R.id.toolbar_add_edit);
    }

    private void loadCategories() {
        categories.clear();
        categories.add("Uncategorized"); // Default category
        categories.addAll(dbHelper.getAllCategories());

        // Add option to create new category
        categories.add("+ Add new category");
    }

    private void setupCategoryDropdown() {
        categoryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, categories);
        autoCompleteCategory.setAdapter(categoryAdapter);

        // Set current category if editing
        if (isEditMode && currentNote != null) {
            String category = currentNote.getCategory();
            if (category != null && !category.isEmpty()) {
                autoCompleteCategory.setText(category, false);
            } else {
                autoCompleteCategory.setText("Uncategorized", false);
            }
        } else {
            autoCompleteCategory.setText("Uncategorized", false);
        }

        autoCompleteCategory.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCategory = parent.getItemAtPosition(position).toString();

            if (selectedCategory.equals("+ Add new category")) {
                showAddCategoryDialog();
            }
        });
    }

    private void showAddCategoryDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add New Category");

        // Set up the input
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setHint("Category name");
        // Add padding
        int padding = (int) (16 * getResources().getDisplayMetrics().density);
        builder.setView(input);
        input.setPadding(padding, padding, padding, padding);

        // Set up the buttons
        builder.setPositiveButton("OK", (dialog, which) -> {
            String newCategory = input.getText().toString().trim();
            if (!newCategory.isEmpty()) {
                // Add to categories if not exists
                if (!categories.contains(newCategory)) {
                    categories.remove("+ Add new category"); // Remove temporarily
                    categories.add(newCategory);
                    categories.add("+ Add new category"); // Add back at the end

                    // Update adapter
                    categoryAdapter = new ArrayAdapter<>(this,
                            android.R.layout.simple_dropdown_item_1line, categories);
                    autoCompleteCategory.setAdapter(categoryAdapter);

                    // Set selected category
                    autoCompleteCategory.setText(newCategory, false);
                }
            }
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.cancel();
            // Reset to previous category or "Uncategorized"
            if (isEditMode && currentNote != null && currentNote.getCategory() != null) {
                autoCompleteCategory.setText(currentNote.getCategory(), false);
            } else {
                autoCompleteCategory.setText("Uncategorized", false);
            }
        });

        builder.show();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(isEditMode ?
                    getString(R.string.edit_note_toolbar_title) :
                    getString(R.string.add_note_toolbar_title));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void checkForEditMode() {
        noteId = getIntent().getIntExtra("note_id", -1);
        if (noteId != -1) {
            isEditMode = true;
            loadNoteData();
        } else {
            isEditMode = false;
            layoutTimestamps.setVisibility(View.GONE);
            buttonSubmit.setText(getString(R.string.button_create_text));
        }
    }

    private void loadNoteData() {
        currentNote = dbHelper.getNoteById(noteId);
        if (currentNote != null) {
            editTextTitle.setText(currentNote.getTitle());
            editTextContent.setText(currentNote.getContent());
            textViewCreatedDate.setText(String.format(getString(R.string.created_date_format),
                    currentNote.getCreatedAt()));
            textViewUpdatedDate.setText(String.format(getString(R.string.updated_date_format),
                    currentNote.getUpdatedAt()));
            layoutTimestamps.setVisibility(View.VISIBLE);
            buttonSubmit.setText(getString(R.string.button_update_text));
        }
    }

    private void setupSubmitButton() {
        buttonSubmit.setOnClickListener(v -> saveOrUpdateNote());
    }

    private void saveOrUpdateNote() {
        if (!validateInput()) {
            return;
        }

        String title = editTextTitle.getText().toString().trim();
        String content = editTextContent.getText().toString().trim();
        String category = autoCompleteCategory.getText().toString().trim();

        if (category.equals("+ Add new category") || category.isEmpty()) {
            category = "Uncategorized";
        }

        if (isEditMode && currentNote != null) {
            currentNote.setTitle(title);
            currentNote.setContent(content);
            currentNote.setCategory(category);
            dbHelper.updateNote(currentNote);
            Toast.makeText(this, R.string.note_updated_message, Toast.LENGTH_SHORT).show();
        } else {
            String currentTime = getCurrentTimestamp();
            Note newNote = new Note();
            newNote.setTitle(title);
            newNote.setContent(content);
            newNote.setCreatedAt(currentTime);
            newNote.setUpdatedAt(currentTime);
            newNote.setFavorite(false);
            newNote.setCategory(category);
            long id = dbHelper.addNote(newNote);
            if (id != -1) {
                Toast.makeText(this, R.string.note_created_message, Toast.LENGTH_SHORT).show();
            }
        }

        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    private boolean validateInput() {
        boolean isValid = true;

        if (TextUtils.isEmpty(editTextTitle.getText())) {
            textInputLayoutTitle.setError(getString(R.string.title_error_message));
            isValid = false;
        } else {
            textInputLayoutTitle.setError(null);
        }

        if (TextUtils.isEmpty(editTextContent.getText())) {
            textInputLayoutContent.setError(getString(R.string.content_error_message));
            isValid = false;
        } else {
            textInputLayoutContent.setError(null);
        }

        return isValid;
    }

    private String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // Check if there are unsaved changes
        if (hasUnsavedChanges()) {
            showUnsavedChangesDialog();
        } else {
            super.onBackPressed();
        }
    }

    private boolean hasUnsavedChanges() {
        if (isEditMode && currentNote != null) {
            String currentTitle = editTextTitle.getText().toString().trim();
            String currentContent = editTextContent.getText().toString().trim();
            String currentCategory = autoCompleteCategory.getText().toString().trim();

            return !currentNote.getTitle().equals(currentTitle) ||
                    !currentNote.getContent().equals(currentContent) ||
                    !currentNote.getCategory().equals(currentCategory);
        } else {
            String currentTitle = editTextTitle.getText().toString().trim();
            String currentContent = editTextContent.getText().toString().trim();

            return !TextUtils.isEmpty(currentTitle) || !TextUtils.isEmpty(currentContent);
        }
    }

    private void showUnsavedChangesDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.unsaved_changes_title)
                .setMessage(R.string.unsaved_changes_message)
                .setPositiveButton(R.string.save, (dialog, which) -> saveOrUpdateNote())
                .setNegativeButton(R.string.discard, (dialog, which) -> finish())
                .create()
                .show();
    }
}