import {Injectable} from '@angular/core';
import * as PouchDB from 'pouchdb';

@Injectable()
export class ItemsService {
  private db;
  private items;

  initDB() {
    this.db = new PouchDB('items', {adapter: 'websql'});
  }

  addItem(item) {
    return this.db.post(item);
  }

  updateItem(item) {
    return this.db.put(item);
  }

  deleteItem(item) {
    return this.db.remove(item);
  }

  getAll() {
    if (!this.items) {
      return this.db.allDocs({include_docs: true})
        .then(docs => {
          this.items = docs.rows.map(row => {
            row.doc.Date = new Date(row.doc.Date);
            return row.doc;
          });

          this.db.changes({live: true, since: 'now', include_docs: true})
            .on('change', this.onDatabaseChange);

          return this.items;
        });
    } else {
      return Promise.resolve(this.items);
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
      change.doc.Date = new Date(change.doc.Date);
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
