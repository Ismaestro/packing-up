import {Injectable, EventEmitter} from '@angular/core';
import {Storage} from '@ionic/storage';

@Injectable()
export class ItemsService {

  public refreshList$: EventEmitter<any>;

  constructor(private storage: Storage) {
    this.refreshList$ = new EventEmitter();
  }

  getAll() {
    return this.storage.get('items');
  }

  addItem(item) {
    return this.storage.get('items').then((items) => {
      items.push(item);
      this.refreshList$.emit(items);
      return this.storage.set('items', items);
    });
  }

  updateItem(id, item, notify?) {
    return this.storage.get('items').then((items) => {
      for (let i = 0; i < items.length; i++) {
        if (items[i].id === id) {
          items[i] = item;
        }
      }

      if (notify) {
        this.refreshList$.emit(items);
      }

      return this.storage.set('items', items);
    });
  }

  deleteItem(item) {
    return this.storage.get('items').then((items) => {
      for (let i = 0; i < items.length; i++) {
        if (items[i].id === item.id) {
          items.splice(i,1);
        }
      }

      this.refreshList$.emit(items);
      return this.storage.set('items', items);
    });
  }

  removeAll() {
    return this.storage.remove('items');
  }

}
