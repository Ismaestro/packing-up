import {Injectable} from '@angular/core';
import * as PouchDB from 'pouchdb';
import * as pouchdbUpsert from 'pouchdb-upsert';

@Injectable()
export class ItemsService {
  private settingsDB;
  private categoriesDB;
  private itemsDB;
  private items;
  private categories;

  constructor() {
    PouchDB.plugin(pouchdbUpsert);
    this.createDBs();
  }

  createDBs() {
    this.settingsDB = new PouchDB('settings', {adapter: 'websql'});
    this.itemsDB = new PouchDB('items', {adapter: 'websql'});
    this.categoriesDB = new PouchDB('categories', {adapter: 'websql'});
  }

  loadInitData() {
    return this.settingsDB.get('initialLoad').catch(() => {
      this.loadCategories();
      this.loadItems();

      this.settingsDB.put({
        _id: 'initialLoad',
        value: true
      });

      return Promise.resolve();
    });
  }

  loadCategories() {
    this.categoriesDB.post({id: 'documentation'});
    this.categoriesDB.post({id: 'clothes'});
    this.categoriesDB.post({id: 'dressing_case'});
    this.categoriesDB.post({id: 'media'});
    this.categoriesDB.post({id: 'mountain'});
    this.categoriesDB.post({id: 'beach'});
    this.categoriesDB.post({id: 'work'});
    this.categoriesDB.post({id: 'kit'});
    this.categoriesDB.post({id: 'other'});
  }

  loadItems() {
    this.itemsDB.post({id: "passport", categoryId: 'documentation'});
    this.itemsDB.post({id: "driver_license", categoryId: 'documentation'});
    this.itemsDB.post({id: "credit_cards", categoryId: 'documentation'});
    this.itemsDB.post({id: "tickets", categoryId: 'documentation'});

    this.itemsDB.post({id: "skirts", categoryId: 'clothes'});
    this.itemsDB.post({id: "jewelry", categoryId: 'clothes'});
    this.itemsDB.post({id: "wristwatch", categoryId: 'clothes'});
    this.itemsDB.post({id: "glasses", categoryId: 'clothes'});
    this.itemsDB.post({id: "footwear", categoryId: 'clothes'});
    this.itemsDB.post({id: "socks", categoryId: 'clothes'});
    this.itemsDB.post({id: "shorts", categoryId: 'clothes'});
    this.itemsDB.post({id: "trousers", categoryId: 'clothes'});
    this.itemsDB.post({id: "underwear", categoryId: 'clothes'});
    this.itemsDB.post({id: "t_shirts", categoryId: 'clothes'});
    this.itemsDB.post({id: "cap", categoryId: 'clothes'});
    this.itemsDB.post({id: "hat", categoryId: 'clothes'});
    this.itemsDB.post({id: "rain_coat", categoryId: 'clothes'});
    this.itemsDB.post({id: "scarf", categoryId: 'clothes'});
    this.itemsDB.post({id: "gloves", categoryId: 'clothes'});
    this.itemsDB.post({id: "night_suit", categoryId: 'clothes'});
    this.itemsDB.post({id: "slippers", categoryId: 'clothes'});
    this.itemsDB.post({id: "scarves", categoryId: 'clothes'});
    this.itemsDB.post({id: "makeup", categoryId: 'clothes'});
    this.itemsDB.post({id: "lipstick", categoryId: 'clothes'});

    this.itemsDB.post({id: "tampons", categoryId: 'dressing_case'});
    this.itemsDB.post({id: "salvaslip", categoryId: 'dressing_case'});
    this.itemsDB.post({id: "cleansing_wipes", categoryId: 'dressing_case'});
    this.itemsDB.post({id: "moisturizer", categoryId: 'dressing_case'});
    this.itemsDB.post({id: "foam_hair", categoryId: 'dressing_case'});
    this.itemsDB.post({id: "conditioner", categoryId: 'dressing_case'});
    this.itemsDB.post({id: "gums", categoryId: 'dressing_case'});
    this.itemsDB.post({id: "nail_polish", categoryId: 'dressing_case'});
    this.itemsDB.post({id: "nail_file", categoryId: 'dressing_case'});
    this.itemsDB.post({id: "perfume", categoryId: 'dressing_case'});
    this.itemsDB.post({id: "bath_gel", categoryId: 'dressing_case'});
    this.itemsDB.post({id: "shampoo", categoryId: 'dressing_case'});
    this.itemsDB.post({id: "sponge", categoryId: 'dressing_case'});
    this.itemsDB.post({id: "dryer", categoryId: 'dressing_case'});
    this.itemsDB.post({id: "comb", categoryId: 'dressing_case'});
    this.itemsDB.post({id: "deodorant", categoryId: 'dressing_case'});
    this.itemsDB.post({id: "toothbrush", categoryId: 'dressing_case'});
    this.itemsDB.post({id: "toothpaste", categoryId: 'dressing_case'});
    this.itemsDB.post({id: "colony", categoryId: 'dressing_case'});
    this.itemsDB.post({id: "gomina", categoryId: 'dressing_case'});
    this.itemsDB.post({id: "creams", categoryId: 'dressing_case'});
    this.itemsDB.post({id: "ear_buds", categoryId: 'dressing_case'});
    this.itemsDB.post({id: "shaving_gel", categoryId: 'dressing_case'});
    this.itemsDB.post({id: "safety_razor", categoryId: 'dressing_case'});
    this.itemsDB.post({id: "nail_clippers", categoryId: 'dressing_case'});

    this.itemsDB.post({id: "map", categoryId: 'mountain'});
    this.itemsDB.post({id: "compass", categoryId: 'mountain'});
    this.itemsDB.post({id: "waist", categoryId: 'mountain'});
    this.itemsDB.post({id: "lantern", categoryId: 'mountain'});
    this.itemsDB.post({id: "binoculars", categoryId: 'mountain'});
    this.itemsDB.post({id: "rope", categoryId: 'mountain'});
    this.itemsDB.post({id: "whistle", categoryId: 'mountain'});
    this.itemsDB.post({id: "food_tools", categoryId: 'mountain'});
    this.itemsDB.post({id: "pencil_and_paper", categoryId: 'mountain'});
    this.itemsDB.post({id: "sleeping_bag", categoryId: 'mountain'});
    this.itemsDB.post({id: "swiss_knife", categoryId: 'mountain'});
    this.itemsDB.post({id: "lighter", categoryId: 'mountain'});
    this.itemsDB.post({id: "thermos", categoryId: 'mountain'});
    this.itemsDB.post({id: "pillow", categoryId: 'mountain'});

    this.itemsDB.post({id: "swimsuit", categoryId: 'beach'});
    this.itemsDB.post({id: "sunscreen", categoryId: 'beach'});
    this.itemsDB.post({id: "after_sun", categoryId: 'beach'});
    this.itemsDB.post({id: "thongs", categoryId: 'beach'});
    this.itemsDB.post({id: "towel", categoryId: 'beach'});
    this.itemsDB.post({id: "parasol", categoryId: 'beach'});
    this.itemsDB.post({id: "mats", categoryId: 'beach'});
    this.itemsDB.post({id: "racquets", categoryId: 'beach'});
    this.itemsDB.post({id: "cards", categoryId: 'beach'});

    this.itemsDB.post({id: "camera", categoryId: 'media'});
    this.itemsDB.post({id: "charger", categoryId: 'media'});
    this.itemsDB.post({id: "mobile", categoryId: 'media'});
    this.itemsDB.post({id: "ipod", categoryId: 'media'});
    this.itemsDB.post({id: "headphones", categoryId: 'media'});
    this.itemsDB.post({id: "radio", categoryId: 'media'});
    this.itemsDB.post({id: "GPS", categoryId: 'media'});
    this.itemsDB.post({id: "tablet", categoryId: 'media'});
    this.itemsDB.post({id: "memory_card", categoryId: 'media'});

    this.itemsDB.post({id: "ties", categoryId: 'work'});
    this.itemsDB.post({id: "breath_mints", categoryId: 'work'});
    this.itemsDB.post({id: "pants", categoryId: 'work'});
    this.itemsDB.post({id: "skirt_suit", categoryId: 'work'});
    this.itemsDB.post({id: "jacket", categoryId: 'work'});
    this.itemsDB.post({id: "shirts", categoryId: 'work'});
    this.itemsDB.post({id: "cufflink", categoryId: 'work'});
    this.itemsDB.post({id: "shoes", categoryId: 'work'});
    this.itemsDB.post({id: "phone", categoryId: 'work'});
    this.itemsDB.post({id: "laptop", categoryId: 'work'});
    this.itemsDB.post({id: "flash_drive", categoryId: 'work'});
    this.itemsDB.post({id: "work_material", categoryId: 'work'});

    this.itemsDB.post({id: "band_aids", categoryId: 'kit'});
    this.itemsDB.post({id: "scissors", categoryId: 'kit'});
    this.itemsDB.post({id: "paracetamol", categoryId: 'kit'});
    this.itemsDB.post({id: "sticking_plaster", categoryId: 'kit'});
    this.itemsDB.post({id: "thermometer", categoryId: 'kit'});
    this.itemsDB.post({id: "antiseptic", categoryId: 'kit'});

    this.itemsDB.post({id: "keys", categoryId: 'other'});
    this.itemsDB.post({id: "cash", categoryId: 'other'});
    this.itemsDB.post({id: "umbrella", categoryId: 'other'});
    this.itemsDB.post({id: "voltage_converter", categoryId: 'other'});
    this.itemsDB.post({id: "anti_mosquito", categoryId: 'other'});
    this.itemsDB.post({id: "travel_locks", categoryId: 'other'});
    this.itemsDB.post({id: "plastic_bags", categoryId: 'other'});
    this.itemsDB.post({id: "condoms", categoryId: 'other'});
    this.itemsDB.post({id: "toys", categoryId: 'other'});
    this.itemsDB.post({id: "baby_food", categoryId: 'other'});
    this.itemsDB.post({id: "book", categoryId: 'other'});
    this.itemsDB.post({id: "snacks", categoryId: 'other'});
    this.itemsDB.post({id: "shoes_insoles", categoryId: 'other'});
    this.itemsDB.post({id: "water", categoryId: 'other'});
    this.itemsDB.post({id: "travel_pillow", categoryId: 'other'});
  }

  addItem(item) {
    return this.itemsDB.post(item);
  }

  updateItem(item) {
    this.itemsDB.get(item._id, (err, doc) => {
      if (err) { return; }

      let newObj = Object.assign({}, item);
      newObj._rev = doc._rev;

      this.itemsDB.put(newObj, (err) => {
        if (err) {
          this.itemsDB.put(newObj, () => { // wtf
          });
        }
      });
    });
  }

  deleteItem(item) {
    this.itemsDB.get(item._id, (err, doc) => {
      if (err) { return; }

      let newObj = {
        _id: item._id,
        _rev: doc._rev,
        id: item.id,
        checked: item.checked
      };

      this.itemsDB.remove(newObj, (err) => {
        if (err) {
          this.itemsDB.remove(newObj, () => {
          });
        }
      });
    });
  }

  getAllItems() {
    if (!this.items) {
      return this.itemsDB.allDocs({include_docs: true})
        .then(docs => {
          this.items = docs.rows.map(row => {
            return row.doc;
          });

          this.itemsDB.changes({live: true, since: 'now', include_docs: true})
            .on('change', this.onDatabaseChange);

          return this.items;
        });
    } else {
      return Promise.resolve(this.items);
    }
  }

  getAllCategories() {
    if (!this.categories) {
      return this.categoriesDB.allDocs({include_docs: true})
        .then(docs => {
          this.categories = docs.rows.map(row => {
            return row.doc;
          });

          return this.categories;
        });
    } else {
      return Promise.resolve(this.categories);
    }
  }

  resetDB() {
    new PouchDB('settings').destroy().then(() => {
      new PouchDB('items').destroy().then(() => {
        new PouchDB('categories').destroy().then(() => {
          document.location.href = 'index.html';
        });
      });
    });

  }

  private onDatabaseChange = (change) => {
    let index = ItemsService.findIndex(this.items, change.id);
    let item = this.items[index];

    if (change.deleted) {
      if (item) {
        this.items.splice(index, 1);
      }
    } else {
      if (item && item._id === change.id) {
        this.items[index] = change.doc;
      } else {
        this.items.splice(index, 0, change.doc);
      }
    }
  };

  private static findIndex(array, id) {
    let low = 0, high = array.length, mid;
    while (low < high) {
      mid = (low + high) >>> 1;
      array[mid]._id < id ? low = mid + 1 : high = mid
    }
    return low;
  }

}
