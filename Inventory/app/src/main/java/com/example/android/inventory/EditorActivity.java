package com.example.android.inventory;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.example.android.inventory.InventoryContract.*;

/**
 * Created by manmohan on 23/6/17.
 */

public class EditorActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private EditText mName;
    private EditText mPrice;
    private TextView mQuantity;
    private Button mButtonAdd;
    private Button mButtonSub;
    private Button mUpload;
    private ImageView mImage;
    private Button mOrder;
    private ActionBar mActionBar;
    private Uri mUri;
    private boolean mItemHasChanged = false;
    private int GET_FROM_GALLERY = 1;

    View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mItemHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mName = (EditText) findViewById(R.id.edit_name);
        mPrice = (EditText) findViewById(R.id.edit_price);
        mQuantity = (TextView) findViewById(R.id.edit_quantity);
        mButtonAdd = (Button) findViewById(R.id.edit_add);
        mButtonSub = (Button) findViewById(R.id.edit_sub);
        mUpload = (Button) findViewById(R.id.edit_image_button);
        mImage = (ImageView) findViewById(R.id.edit_image);
        mOrder = (Button) findViewById(R.id.edit_order);

        mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);

        mName.setOnTouchListener(mTouchListener);
        mPrice.setOnTouchListener(mTouchListener);
        mButtonAdd.setOnTouchListener(mTouchListener);
        mButtonSub.setOnTouchListener(mTouchListener);
        mUpload.setOnTouchListener(mTouchListener);

        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.valueOf(mQuantity.getText().toString());
                quantity++;
                mQuantity.setText(String.valueOf(quantity));
            }
        });

        mButtonSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.valueOf(mQuantity.getText().toString());
                if (quantity > 0) {
                    quantity--;
                }
                mQuantity.setText(String.valueOf(quantity));
            }
        });

        mOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(EditorActivity.this, "Please fill the name of the product", Toast.LENGTH_SHORT).show();
                    return;
                }
                String content = "I would like to get some more of " + name + "\nThank You";
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Order of " + name);
                intent.putExtra(Intent.EXTRA_TEXT, content);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        mUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });

        mUri = getIntent().getData();
        if (mUri == null) {
            setTitle("Add Item");
            invalidateOptionsMenu();
        } else {
            setTitle("Edit Item");
            getLoaderManager().initLoader(1, null, this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Detects request codes
        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (bitmap != null) {
                mImage.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                insertItem();
                return true;
            case R.id.action_delete:
                showConfirm();
                return true;
            case android.R.id.home:
                if (!mItemHasChanged) {
                    NavUtils.navigateUpFromSameTask(this);
                    return true;
                }
                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User clicked "Discard" button, navigate to parent activity.
                                NavUtils.navigateUpFromSameTask(EditorActivity.this);
                            }
                        };

                // Show a dialog that notifies the user they have unsaved changes
                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showUnsavedChangesDialog(DialogInterface.OnClickListener discardButtonClickListener) {
        // Create an AlertDialog.Builder and set the message, and click listeners
        // for the positive and negative buttons on the dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Discard changes ?");
        builder.setPositiveButton("Discard", discardButtonClickListener);
        builder.setNegativeButton("Keep Editing", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Keep editing" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void insertItem() {
        String name = mName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(EditorActivity.this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        String price = mPrice.getText().toString().trim();
        if (TextUtils.isEmpty(price)) {
            Toast.makeText(EditorActivity.this, "Price cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        String quantity = mQuantity.getText().toString().trim();
        if (TextUtils.isEmpty(quantity) || Integer.valueOf(quantity) == 0) {
            Toast.makeText(EditorActivity.this, "Quantity cannot be 0", Toast.LENGTH_SHORT).show();
            return;
        }

        BitmapDrawable image = (BitmapDrawable) mImage.getDrawable();
        Bitmap bitmap = image.getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] imageInByte = stream.toByteArray();

        ContentValues values = new ContentValues();
        values.put(InventoryEntry.COLUMN_NAME, name);
        values.put(InventoryEntry.COLUMN_PRICE, Integer.valueOf(price));
        values.put(InventoryEntry.COLUMN_QUANTITY, Integer.valueOf(quantity));
        values.put(InventoryEntry.COLUMN_IMAGE, imageInByte);

        if (mUri == null) {
            getContentResolver().insert(InventoryEntry.CONTENT_URI, values);
        } else {
            Log.v("Insert Method", "uri is " + mUri);
            getContentResolver().update(mUri, values, null, null);
        }

        finish();
    }

    private void showConfirm() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to Delete ?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Delete" button, so delete the pet.
                deleteItem();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                // and continue editing the pet.
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteItem() {
        int rows = 0;
        if (mUri != null) {
            rows = getContentResolver().delete(mUri, null, null);
        }

        if (rows == 0) {
            Toast.makeText(this, "Deletion Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        // If this is a new pet, hide the "Delete" menu item.
        if (mUri == null) {
            MenuItem menuItem = menu.findItem(R.id.action_delete);
            menuItem.setVisible(false);
        }
        return true;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {InventoryEntry._ID, InventoryEntry.COLUMN_NAME,
                InventoryEntry.COLUMN_QUANTITY, InventoryEntry.COLUMN_PRICE,
                InventoryEntry.COLUMN_IMAGE};
        Log.v("Editor Cursor Loader", "uri is " + mUri);
        return new CursorLoader(this, mUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex(InventoryEntry.COLUMN_NAME));
            String price = cursor.getString(cursor.getColumnIndex(InventoryEntry.COLUMN_PRICE));
            String quantity = cursor.getString(cursor.getColumnIndex(InventoryEntry.COLUMN_QUANTITY));

            if (cursor.getBlob(cursor.getColumnIndex(InventoryEntry.COLUMN_IMAGE)) != null) {
                byte[] image = cursor.getBlob(cursor.getColumnIndex(InventoryEntry.COLUMN_IMAGE));
                Bitmap bitmapImage = BitmapFactory.decodeByteArray(image, 0, image.length);
                mImage.setImageBitmap(bitmapImage);
            }

            mName.setText(name);
            mPrice.setText(price);
            mQuantity.setText(quantity);
        }
        else {
            Toast.makeText(EditorActivity.this, "Rows not found", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
