import {Injectable} from '@angular/core';
import {Storage} from '@ionic/storage';

@Injectable()
export class ItemsService {

  constructor(private storage: Storage) {
  }

  getAll() {
    return this.storage.get('items');
  }

  addItem(item) {
    return this.storage.get('items').then((items) => {
      items.push(item);
      return this.storage.set('items', items);
    });
  }

  updateItem(newItem) {
    return this.storage.get('items').then((items) => {
      for (let i = 0; i < items.length; i++) {
        if (items[i].id === newItem.id) {
          items[i] = newItem;
        }
      }

      return this.storage.set('items', items);
    });
  }

  deleteItem(itemToRemove) {
    return this.storage.get('items').then((items) => {
      console.log(items.length);

      for (let i = 0; i < items.length; i++) {
        if (items[i].id === itemToRemove.id) {
          items.splice(i,1);
        }
      }

      console.log(items.length);
      return this.storage.set('items', items);
    });
  }

  removeAll() {
    return this.storage.remove('items');
  }

}
