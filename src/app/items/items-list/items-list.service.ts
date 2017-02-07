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
    this.settingsDB = new PouchDB('settings', {adapter: 'websql'});
    this.itemsDB = new PouchDB('items', {adapter: 'websql'});
    this.categoriesDB = new PouchDB('categories', {adapter: 'websql'});
  }

  loadInitData() {
    return this.settingsDB.get('initialLoad').catch(() => {
      this.itemsDB.post({name: 'asd1', categoryId: 'A'});
      this.itemsDB.post({name: 'asd2', categoryId: 'B'});
      this.categoriesDB.post({id: 'A', name: 'A bla bla bla'});
      this.categoriesDB.post({id: 'B', name: 'B bla bla bla'});

      this.settingsDB.put({
        _id: 'initialLoad',
        value: true
      });

      return Promise.resolve();
    });
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
        name: item.name,
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
