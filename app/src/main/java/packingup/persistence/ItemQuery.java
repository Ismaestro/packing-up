package packingup.persistence;

import packingup.core.activities.R;

public class ItemQuery {

	public static enum Category {
		DOCUMENTATION, CLOTHES, DRESSING_CASE, MEDIA, MOUNT, BEACH, WORK, KIT, OTHERS;
	}

	public static enum Gender {
		WOMEN, MEN, CHILDREN, ALL;

		public static String getGenderFromString(String stringGender) {
			if (stringGender.equals(R.string.gender_women))
				return WOMEN.toString();
			else if (stringGender.equals(R.string.gender_men))
				return MEN.toString();
			else if (stringGender.equals(R.string.gender_children))
				return CHILDREN.toString();
			else if (stringGender.equals(R.string.gender_all))
				return ALL.toString();

			return null;
		}
	}

	protected static final String DATABASE_CREATE = "create table " + ItemDataSource.TABLE_ITEMS + "( "
			+ ItemDataSource.COLUMN_NAME + " text primary key, " + ItemDataSource.COLUMN_NAME_ES
			+ " text not null, " + ItemDataSource.COLUMN_CATEGORY + " text not null, "
			+ ItemDataSource.COLUMN_GENDER + " text not null);";

	protected static final String DATABASE_DROP = "DROP TABLE IF EXISTS " + ItemDataSource.TABLE_ITEMS;

	// @formatter:off
	protected static final String DATABASE_INSERT_DATA = "insert into "
			+ ItemDataSource.TABLE_ITEMS + " select 'Identification' as '"
			+ ItemDataSource.COLUMN_NAME + "', 'DNI' as '"
			+ ItemDataSource.COLUMN_NAME_ES + "', '"
			+ Category.DOCUMENTATION + "' as '"
			+ ItemDataSource.COLUMN_CATEGORY + "', '"
			+ Gender.ALL + "' as '" + ItemDataSource.COLUMN_GENDER + "' "
			+ generateStringInsertItem("Passport", "Pasaporte", Category.DOCUMENTATION, Gender.ALL)
			+ generateStringInsertItem("Driver license", "Permiso de conducir", Category.DOCUMENTATION, Gender.ALL)
			+ generateStringInsertItem("Credit cards", "Tarjetas de crédito", Category.DOCUMENTATION, Gender.ALL)
			+ generateStringInsertItem("Tickets", "Billetes", Category.DOCUMENTATION, Gender.ALL)
			+ generateStringInsertItem("Wristwatch", "Reloj", Category.CLOTHES, Gender.ALL)
			+ generateStringInsertItem("Glasses", "Gafas", Category.CLOTHES, Gender.ALL)
			+ generateStringInsertItem("Footwear", "Calzado", Category.CLOTHES, Gender.ALL)
			+ generateStringInsertItem("Socks", "Calcetines", Category.CLOTHES, Gender.ALL)
			+ generateStringInsertItem("Shorts", "Pantalones cortos", Category.CLOTHES, Gender.ALL)
			+ generateStringInsertItem("Trousers", "Pantalones largos", Category.CLOTHES, Gender.ALL)
			+ generateStringInsertItem("Skirts", "Faldas", Category.CLOTHES, Gender.WOMEN)
			+ generateStringInsertItem("Underwear", "Ropa interior", Category.CLOTHES, Gender.ALL)
			+ generateStringInsertItem("T-Shirts", "Camisetas", Category.CLOTHES, Gender.ALL)
			+ generateStringInsertItem("Cap", "Gorra", Category.CLOTHES, Gender.MEN)
			+ generateStringInsertItem("Hat", "Sombrero", Category.CLOTHES, Gender.WOMEN)
			+ generateStringInsertItem("Rain coat", "Cazadora", Category.CLOTHES, Gender.ALL)
			+ generateStringInsertItem("Scarf", "Bufanda", Category.CLOTHES, Gender.ALL)
			+ generateStringInsertItem("Gloves", "Guantes", Category.CLOTHES, Gender.ALL)
			+ generateStringInsertItem("Baby clothes", "Ropa niño", Category.CLOTHES, Gender.CHILDREN)
			+ generateStringInsertItem("Diapers", "Pañales", Category.CLOTHES, Gender.CHILDREN)
			+ generateStringInsertItem("Night suit", "Pijama", Category.CLOTHES, Gender.ALL)
			+ generateStringInsertItem("Slippers", "Zapatillas", Category.CLOTHES, Gender.ALL)
			+ generateStringInsertItem("Jewelry", "Bisutería", Category.CLOTHES, Gender.WOMEN)
			+ generateStringInsertItem("Scarves", "Pañuelos/Fulares", Category.CLOTHES, Gender.WOMEN)
			+ generateStringInsertItem("Makeup", "Maquillaje", Category.CLOTHES, Gender.WOMEN)
			+ generateStringInsertItem("Lipstick", "Barra de labios", Category.CLOTHES, Gender.WOMEN)
			+ generateStringInsertItem("Tampons/Compresses", "Tampones/Compresas", Category.DRESSING_CASE, Gender.WOMEN)
			+ generateStringInsertItem("Salvaslip", "Salvaslip", Category.DRESSING_CASE, Gender.WOMEN)
			+ generateStringInsertItem("Cleansing wipes", "Toallitas desmaquillantes", Category.DRESSING_CASE, Gender.WOMEN)
			+ generateStringInsertItem("Moisturizer", "Crema hidratante", Category.DRESSING_CASE, Gender.WOMEN)
			+ generateStringInsertItem("Foam hair", "Espuma", Category.DRESSING_CASE, Gender.WOMEN)
			+ generateStringInsertItem("Conditioner", "Acondicionador", Category.DRESSING_CASE, Gender.WOMEN)
			+ generateStringInsertItem("Gums/Hairpins/Tiaras", "Gomas/Horquillas/Diademas", Category.CLOTHES, Gender.WOMEN)
			+ generateStringInsertItem("Nail polish", "Pintauñas", Category.DRESSING_CASE, Gender.WOMEN)
			+ generateStringInsertItem("Nail file", "Lima uñas", Category.DRESSING_CASE, Gender.WOMEN)
			+ generateStringInsertItem("Perfume", "Perfume", Category.DRESSING_CASE, Gender.WOMEN)
			+ generateStringInsertItem("Bath gel", "Gel de baño", Category.DRESSING_CASE, Gender.ALL)
			+ generateStringInsertItem("Shampoo", "Champú", Category.DRESSING_CASE, Gender.ALL)
			+ generateStringInsertItem("Sponge", "Esponja", Category.DRESSING_CASE, Gender.ALL)
			+ generateStringInsertItem("Dryer", "Secador", Category.DRESSING_CASE, Gender.ALL)
			+ generateStringInsertItem("Comb", "Peine", Category.DRESSING_CASE, Gender.ALL)
			+ generateStringInsertItem("Deodorant", "Desodorante", Category.DRESSING_CASE, Gender.ALL)
			+ generateStringInsertItem("Toothbrush", "Cepillo de dientes", Category.DRESSING_CASE, Gender.ALL)
			+ generateStringInsertItem("Toothpaste", "Pasta de dientes", Category.DRESSING_CASE, Gender.ALL)
			+ generateStringInsertItem("Colony", "Colonia", Category.DRESSING_CASE, Gender.ALL)
			+ generateStringInsertItem("Gomina", "Gomina", Category.DRESSING_CASE, Gender.ALL)
			+ generateStringInsertItem("Creams", "Cremas", Category.DRESSING_CASE, Gender.ALL)
			+ generateStringInsertItem("Ear buds", "Bastoncillos", Category.DRESSING_CASE, Gender.ALL)
			+ generateStringInsertItem("Shaving gel", "Gel/Espuma afeitar", Category.DRESSING_CASE, Gender.MEN)
			+ generateStringInsertItem("Safety razor", "Maquina de afeitar", Category.DRESSING_CASE, Gender.MEN)
			+ generateStringInsertItem("Nail clippers", "Cortauñas", Category.DRESSING_CASE, Gender.ALL)
			+ generateStringInsertItem("Map", "Mapa", Category.MOUNT, Gender.ALL)
			+ generateStringInsertItem("Compass", "Brújula", Category.MOUNT, Gender.ALL)
			+ generateStringInsertItem("Waist", "Riñonera", Category.MOUNT, Gender.ALL)
			+ generateStringInsertItem("Lantern", "Linterna", Category.MOUNT, Gender.ALL)
			+ generateStringInsertItem("Binoculars", "Prismáticos", Category.MOUNT, Gender.ALL)
			+ generateStringInsertItem("Rope", "Cuerda", Category.MOUNT, Gender.ALL)
			+ generateStringInsertItem("Whistle", "Silbato", Category.MOUNT, Gender.ALL)
			+ generateStringInsertItem("Food tools", "Herramientas para comer", Category.MOUNT, Gender.ALL)
			+ generateStringInsertItem("Pencil and paper", "Papel y lapiz", Category.MOUNT, Gender.ALL)
			+ generateStringInsertItem("Sleeping bag", "Saco de dormir", Category.MOUNT, Gender.ALL)
			+ generateStringInsertItem("Swiss Army knife", "Navaja suiza", Category.MOUNT, Gender.ALL)
			+ generateStringInsertItem("Lighter", "Encendedor/Mechero", Category.MOUNT, Gender.ALL)
			+ generateStringInsertItem("Thermos", "Termo", Category.MOUNT, Gender.ALL)
			+ generateStringInsertItem("Fleece", "Forro polar", Category.MOUNT, Gender.ALL)
			+ generateStringInsertItem("Pillow", "Almohada", Category.MOUNT, Gender.ALL)
			+ generateStringInsertItem("Swimsuit", "Bañador", Category.BEACH, Gender.ALL)
			+ generateStringInsertItem("Sunscreen", "Protector solar", Category.BEACH, Gender.ALL)
			+ generateStringInsertItem("After sun", "After sun", Category.BEACH, Gender.ALL)
			+ generateStringInsertItem("Thongs", "Chanclas", Category.BEACH, Gender.ALL)
			+ generateStringInsertItem("Towel", "Toalla", Category.BEACH, Gender.ALL)
			+ generateStringInsertItem("Parasol", "Sombrilla", Category.BEACH, Gender.ALL)
			+ generateStringInsertItem("Mats", "Esterillas", Category.BEACH, Gender.ALL)
			+ generateStringInsertItem("Racquets", "Raquetas", Category.BEACH, Gender.ALL)
			+ generateStringInsertItem("Cards", "Cartas", Category.BEACH, Gender.ALL)
			+ generateStringInsertItem("Camera", "Cámara de fotos", Category.MEDIA, Gender.ALL)
			+ generateStringInsertItem("Charger/Battery", "Cargador/Bateria", Category.MEDIA, Gender.ALL)
			+ generateStringInsertItem("Mobile/Smartphone", "Movil/Smartphone", Category.MEDIA, Gender.ALL)
			+ generateStringInsertItem("Ipod/MP5", "Ipod/MP5", Category.MEDIA, Gender.ALL)
			+ generateStringInsertItem("Headphones", "Auriculares", Category.MEDIA, Gender.ALL)
			+ generateStringInsertItem("Radio", "Radio", Category.MEDIA, Gender.ALL)
			+ generateStringInsertItem("GPS", "GPS", Category.MEDIA, Gender.ALL)
			+ generateStringInsertItem("Tablet", "Tablet", Category.MEDIA, Gender.ALL)
			+ generateStringInsertItem("Memory card", "Tarjetas de memoria", Category.MEDIA, Gender.ALL)
			+ generateStringInsertItem("Ties", "Corbatas", Category.WORK, Gender.MEN)
			+ generateStringInsertItem("Business cards", "Tarjetas de trabajo", Category.WORK, Gender.ALL)
			+ generateStringInsertItem("Breath mints", "Caramelos para el aliento", Category.WORK, Gender.ALL)
			+ generateStringInsertItem("A pencil and pad", "Lapiz y libreta", Category.WORK, Gender.ALL)
			+ generateStringInsertItem("Pants", "Pantalones de traje", Category.WORK, Gender.MEN)
			+ generateStringInsertItem("Skirt suit", "Falda de traje", Category.WORK, Gender.WOMEN)
			+ generateStringInsertItem("Jacket", "Chaqueta de traje", Category.WORK, Gender.ALL)
			+ generateStringInsertItem("Shirts", "Camisas", Category.WORK, Gender.ALL)
			+ generateStringInsertItem("Cufflink", "Gemelos (camisa)", Category.WORK, Gender.MEN)
			+ generateStringInsertItem("Shoes", "Zapatos", Category.WORK, Gender.ALL)
			+ generateStringInsertItem("Phone", "Teléfono de trabajo", Category.WORK, Gender.ALL)
			+ generateStringInsertItem("Extra battery", "Batería extra", Category.WORK, Gender.ALL)
			+ generateStringInsertItem("Laptop", "Portátil", Category.WORK, Gender.ALL)
			+ generateStringInsertItem("USB flash drive", "Lápiz USB", Category.WORK, Gender.ALL)
			+ generateStringInsertItem("Work material", "Material necesario", Category.WORK, Gender.ALL)
			+ generateStringInsertItem("Band-aids", "Tiritas", Category.KIT, Gender.ALL)
			+ generateStringInsertItem("Scissors", "Tijeras", Category.KIT, Gender.ALL)
			+ generateStringInsertItem("Paracetamol/Aspirin", "Paracetamol/Aspirinas", Category.KIT, Gender.ALL)
			+ generateStringInsertItem("Sticking plaster", "Esparadrapo", Category.KIT, Gender.ALL)
			+ generateStringInsertItem("Thermometer", "Termómetro", Category.KIT, Gender.ALL)
			+ generateStringInsertItem("Antiseptic", "Antiséptico", Category.KIT, Gender.ALL)
			+ generateStringInsertItem("Keys", "Llaves", Category.OTHERS, Gender.ALL)
			+ generateStringInsertItem("Cash", "Dinero en efectivo", Category.OTHERS, Gender.ALL)
			+ generateStringInsertItem("Umbrella", "Paraguas", Category.OTHERS, Gender.ALL)
			+ generateStringInsertItem("Voltage converter", "Adaptador de corriente", Category.OTHERS, Gender.ALL)
			+ generateStringInsertItem("Anti-mosquito", "Antimosquitos", Category.OTHERS, Gender.ALL)
			+ generateStringInsertItem("Travel locks", "Candado de maleta", Category.OTHERS, Gender.ALL)
			+ generateStringInsertItem("Plastic bags", "Bolsas de plástico", Category.OTHERS, Gender.ALL)
			+ generateStringInsertItem("Tissues", "Pañuelos sonarse", Category.OTHERS, Gender.ALL)
			+ generateStringInsertItem("Condoms", "Preservativos", Category.OTHERS, Gender.MEN)
			+ generateStringInsertItem("Toys", "Jueguetes", Category.OTHERS, Gender.CHILDREN)
			+ generateStringInsertItem("Baby food", "Comida niño", Category.OTHERS, Gender.CHILDREN)
			+ generateStringInsertItem("Book", "Libro", Category.OTHERS, Gender.ALL)
			+ generateStringInsertItem("Snacks", "Tentempié", Category.OTHERS, Gender.ALL)
			+ generateStringInsertItem("Shoes insoles", "Plantillas calzado", Category.OTHERS, Gender.ALL)
			+ generateStringInsertItem("Water", "Agua", Category.OTHERS, Gender.ALL)
			+ generateStringInsertItem("Travel pillow", "Almohada de viaje", Category.OTHERS, Gender.ALL)
			+ ";";

	// @formatter:on
	private static String generateStringInsertItem(String name, String name_es, Category category,
			Gender gender) {
		return "union select '" + name + "', '" + name_es + "', '" + category + "', '" + gender + "'";
	}
}
