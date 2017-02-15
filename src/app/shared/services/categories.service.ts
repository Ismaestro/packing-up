import {Injectable, EventEmitter} from '@angular/core';
import {Storage} from '@ionic/storage';
import {ItemsService} from "./items.service";

@Injectable()
export class CategoriesService {

  public refreshList$: EventEmitter<any>;

  constructor(private storage: Storage, private itemsService: ItemsService) {
    this.refreshList$ = new EventEmitter();
  }

  getAll() {
    return this.storage.get('categories');
  }

  addCategory(category) {
    return this.storage.get('categories').then((categories) => {
      categories.push(category);
      this.refreshList$.emit(categories);
      return this.storage.set('categories', categories);
    });
  }

  updateCategory(id, category) {
    return this.storage.get('categories').then((categories) => {
      return this.storage.get('items').then((items) => {
        for (let i = 0; i < categories.length; i++) {
          if (categories[i].id === id) {
            categories[i] = category;
          }
        }

        for (let i = 0; i < items.length; i++) {
          if (items[i].categoryId === id) {
            items[i].categoryId = category.id;
          }
        }

        return this.storage.set('items', items).then(() => {
          this.itemsService.refreshList$.emit(items);
          this.refreshList$.emit(categories);
          return this.storage.set('categories', categories);
        });
      });
    });
  }

  deleteCategory(category) {
    return this.storage.get('categories').then((categories) => {
      return this.storage.get('items').then((items) => {
        for (let i = 0; i < categories.length; i++) {
          if (categories[i].id === category.id) {
            categories.splice(i, 1);
          }
        }

        for (let i = 0; i < items.length; i++) {
          if (items[i].categoryId === category.id) {
            delete items[i];
          }
        }

        items = this.cleanArray(items);

        return this.storage.set('items', items).then(() => {
          this.itemsService.refreshList$.emit(items);
          this.refreshList$.emit(categories);
          return this.storage.set('categories', categories);
        });
      });
    });
  }

  removeAll() {
    return this.storage.remove('categories');
  }

  private cleanArray(actual) {
  let newArray = [];
  for (let i = 0; i < actual.length; i++) {
    if (actual[i]) {
      newArray.push(actual[i]);
    }
  }
  return newArray;
}

}
