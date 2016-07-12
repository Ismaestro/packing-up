package packingup.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import packingup.core.activities.R;
import packingup.model.Item;
import packingup.persistence.ItemQuery.Category;
import packingup.persistence.ItemQuery.Gender;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ItemDataSource {

	protected static final String TABLE_ITEMS = "items";

	protected static final String COLUMN_NAME = "name";
	protected static final String COLUMN_NAME_ES = "name_es";
	protected static final String COLUMN_CATEGORY = "category";
	protected static final String COLUMN_GENDER = "gender";

	private final String[] allColumns = { COLUMN_NAME, COLUMN_NAME_ES, COLUMN_CATEGORY, COLUMN_GENDER };

	public static String[] workCategories = { ItemQuery.Category.DOCUMENTATION.toString(),
			ItemQuery.Category.CLOTHES.toString(), ItemQuery.Category.DRESSING_CASE.toString(),
			ItemQuery.Category.MEDIA.toString(), ItemQuery.Category.WORK.toString(),
			ItemQuery.Category.OTHERS.toString() };

	private String[] womenGender = { ItemQuery.Gender.WOMEN.toString() };
	private String[] menGender = { ItemQuery.Gender.MEN.toString() };
	private String[] childrenGender = { ItemQuery.Gender.CHILDREN.toString() };
	private String[] allGender = { ItemQuery.Gender.ALL.toString() };

	private SQLiteDatabase database;

	private final SQLiteHelper helper;

	private Locale locale;
	private Context context;

	public ItemDataSource(final Context context, Locale locale) {
		this.context = context;
		this.helper = new SQLiteHelper(context);
		this.locale = locale;
	}

	private void open() throws SQLException {
		database = helper.getWritableDatabase();
	}

	private void close() {
		helper.close();
	}

	public long createItem(String name, String category, String gender) {
		final ContentValues values = new ContentValues();

		name = name.replaceAll("'", "\'");

		values.put(COLUMN_NAME, name);
		values.put(COLUMN_NAME_ES, name);
		values.put(COLUMN_CATEGORY, getCategoryFromString(category));
		values.put(COLUMN_GENDER, getGenderFromString(gender));

		open();
		long result = database.insert(TABLE_ITEMS, null, values);
		close();
		return result;
	}

	public List<Item> getHolidaysItems() {
		final List<Item> itemList = new ArrayList<Item>();
		open();
		final Cursor cursor = database.query(TABLE_ITEMS, allColumns, null, null, null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			final Item item = new Item(cursor.getString(0), cursor.getString(1), cursor.getString(2),
					cursor.getString(3), locale);
			itemList.add(item);
			cursor.moveToNext();
		}

		cursor.close();
		close();
		return itemList;
	}

	public List<Item> getWorkItems() {
		final List<Item> itemList = new ArrayList<Item>();
		open();
		String whereSentence = COLUMN_CATEGORY + "=? OR " + COLUMN_CATEGORY + "=? OR " + COLUMN_CATEGORY
				+ "=? OR " + COLUMN_CATEGORY + "=? OR " + COLUMN_CATEGORY + "=? OR " + COLUMN_CATEGORY
				+ "=?";
		Cursor cursor = database.query(TABLE_ITEMS, allColumns, whereSentence, workCategories, null,
				null, null, null);
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			final Item item = new Item(cursor.getString(0), cursor.getString(1), cursor.getString(2),
					cursor.getString(3), locale);
			itemList.add(item);
			cursor.moveToNext();
		}

		cursor.close();
		close();
		return itemList;
	}

	public List<Item> getItemsByGender(Gender gender) {
		final List<Item> itemList = new ArrayList<Item>();
		open();
		String whereSentence = COLUMN_GENDER + "=?";
		String[] genderArray = null;
		switch (gender) {
		case WOMEN:
			genderArray = womenGender;
			break;
		case MEN:
			genderArray = menGender;
			break;
		case CHILDREN:
			genderArray = childrenGender;
			break;
		case ALL:
			genderArray = allGender;
			break;
		}

		if (genderArray != null) {
			Cursor cursor = database.query(TABLE_ITEMS, allColumns, whereSentence, genderArray, null,
					null, null, null);
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				final Item item = new Item(cursor.getString(0), cursor.getString(1),
						cursor.getString(2), cursor.getString(3), locale);
				itemList.add(item);
				cursor.moveToNext();
			}

			cursor.close();
		}
		close();
		return itemList;
	}

	public Item[] getItemsByCategory(Category category, List<Item> list) {
		List<Item> copy = new ArrayList<Item>(list);
		for (Item i : list)
			if (!i.getCategory().equals(category.toString()))
				copy.remove(i);

		return getArrayOfItems(copy);
	}

	private Item[] getArrayOfItems(List<Item> items) {
		Item[] result = new Item[items.size()];
		for (int i = 0; i < items.size(); i++)
			result[i] = items.get(i);

		return result;
	}

	private String getCategoryFromString(String category) {
		if (category.equals(context.getString(R.string.category_documentation)))
			return Category.DOCUMENTATION.toString();
		else if (category.equals(context.getString(R.string.category_clothes)))
			return Category.CLOTHES.toString();
		else if (category.equals(context.getString(R.string.category_dressing_case)))
			return Category.DRESSING_CASE.toString();
		else if (category.equals(context.getString(R.string.category_media)))
			return Category.MEDIA.toString();
		else if (category.equals(context.getString(R.string.category_mount)))
			return Category.MOUNT.toString();
		else if (category.equals(context.getString(R.string.category_beach)))
			return Category.BEACH.toString();
		else if (category.equals(context.getString(R.string.category_work)))
			return Category.WORK.toString();
		else if (category.equals(context.getString(R.string.category_kit)))
			return Category.KIT.toString();
		else if (category.equals(context.getString(R.string.category_others)))
			return Category.OTHERS.toString();

		return null;
	}

	private String getGenderFromString(String gender) {
		if (gender.equals(context.getString(R.string.gender_women)))
			return Gender.WOMEN.toString();
		else if (gender.equals(context.getString(R.string.gender_men)))
			return Gender.MEN.toString();
		else if (gender.equals(context.getString(R.string.gender_children)))
			return Gender.CHILDREN.toString();
		else if (gender.equals(context.getString(R.string.gender_all)))
			return Gender.ALL.toString();

		return null;
	}

	public boolean removeItem(Item item) {
		boolean b = false;
		open();
		switch (locale.getLanguage()) {
		case "en":
			b = database.delete(TABLE_ITEMS, COLUMN_NAME + "='" + item.getName() + "'", null) > 0;
			break;
		case "es":
			b = database.delete(TABLE_ITEMS, COLUMN_NAME_ES + "='" + item.getName_es() + "'", null) > 0;
			break;
		}
		close();
		return b;
	}

	public boolean existsItem(String name) {
		boolean b = false;
		String query = "";
		open();
		switch (locale.getLanguage()) {
		case "en":
			query = "select * from " + TABLE_ITEMS + " where " + COLUMN_NAME + "='" + name + "'";
			break;
		case "es":
			query = "select * from " + TABLE_ITEMS + " where " + COLUMN_NAME_ES + "='" + name + "'";
			break;
		}

		Cursor cursor = database.rawQuery(query, null);
		if (cursor.getCount() != 0) {
			cursor.close();
			b = true;
		}
		close();
		return b;
	}

}
