import {Injectable} from '@angular/core';
import {Storage} from '@ionic/storage';

@Injectable()
export class CategoriesService {

  constructor(private storage: Storage) {
  }

  getAll() {
    return this.storage.get('categories');
  }

  addCategory(category) {
    return this.storage.get('categories').then((category) => {
      category.push(category);
      return this.storage.set('categories', category);
    });
  }

  updateCategory(newCategory) {
    return this.storage.get('categories').then((categories) => {
      for (let category of categories) {
        if (category.id === newCategory.id) {
          category = newCategory;
        }
      }
      return this.storage.set('categories', categories);
    });
  }

  deleteCategory(categoryToRemove) {
    return this.storage.get('categories').then((category) => {
      category = category.filter(category => category.id !== categoryToRemove.id);
      return this.storage.set('categories', category);
    });
  }

  removeAll() {
    return this.storage.remove('categories');
  }

}
