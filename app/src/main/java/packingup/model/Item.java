package packingup.model;

import java.io.Serializable;
import java.util.Locale;

public class Item implements Cloneable, Comparable<Item>, Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String name_es;
	private String category;
	private String gender;
	private boolean isChecked;
	private Locale locale;

	public Item(String name, String name_es, String category, String gender, Locale locale) {
		this.name = name;
		this.name_es = name_es;
		this.category = category;
		this.gender = gender;
		this.locale = locale;
		this.isChecked = false;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName_es() {
		return name_es;
	}

	public void setName_es(String name_es) {
		this.name_es = name_es;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public String getText() {
		switch (locale.getLanguage()) {
		case "en":
			return name;
		case "es":
			return name_es;
		}
		return "";
	}

	@Override
	public int compareTo(Item item) {
		switch (locale.getLanguage()) {
		case "en":
			return this.name.compareTo(item.getName());
		case "es":
			return this.name_es.compareTo(item.getName_es());
		}
		return 0;

	}

	@Override
	public String toString() {
		return name_es;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
