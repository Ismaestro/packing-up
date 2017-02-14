import {Injectable, EventEmitter} from '@angular/core';
import {Storage} from '@ionic/storage';

@Injectable()
export class CategoriesService {

  public refreshList$: EventEmitter<any>;

  constructor(private storage: Storage) {
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
      for (let i = 0; i < categories.length; i++) {
        if (categories[i].id === id) {
          categories[i] = category;
        }
      }

      this.refreshList$.emit(categories);
      return this.storage.set('categories', categories);
    });
  }

  deleteCategory(category) {
    return this.storage.get('categories').then((categories) => {
      for (let i = 0; i < categories.length; i++) {
        if (categories[i].id === category.id) {
          categories.splice(i,1);
        }
      }

      this.refreshList$.emit(categories);
      return this.storage.set('categories', categories);
    });
  }

  removeAll() {
    return this.storage.remove('categories');
  }

}
