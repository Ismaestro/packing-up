package packingup.core.utils;

import android.content.Context;
import android.graphics.Typeface;

public class Font {
	public static Typeface getTennesseeFont(Context context) {
		return Typeface.createFromAsset(context.getAssets(), "fonts/TennesseeSF.ttf");
	}

	public static Typeface getEbrimaFont(Context context) {
		return Typeface.createFromAsset(context.getAssets(), "fonts/Ebrima.ttf");
	}
}
