import {Component} from "@angular/core";
import {ModalController} from 'ionic-angular';
import {CategoriesService} from '../../shared/services/categories.service';
import {ItemsService} from '../../shared/services/items.service';
import {ItemDetailsPage} from '../../pages/item-detail/item-detail.component';
import {CategoryDetailsPage} from "../../pages/category-detail/category-detail.component";

@Component({
  selector: 'items-list',
  templateUrl: 'items-list.component.html'
})
export class ItemsList {
  public items = [];
  public categories = [];
  public itemsChecked = 0;

  constructor(private categoriesService: CategoriesService,
              private itemsService: ItemsService,
              private modalCtrl: ModalController) {
    this.itemsService.refreshList$.subscribe(
      items => this.refreshItemsList(items));

    this.categoriesService.refreshList$.subscribe(
      categories => this.refreshCategoriesList(categories));

    this.loadCategories();
    this.loadItems();
  }

  loadCategories() {
    this.categoriesService.getAll()
      .then(categories => {
        this.categories = categories;
      });
  }

  loadItems() {
    this.itemsService.getAll()
      .then(items => {
        this.items = items;
        this.calculateItemsChecked();
      });
  }

  itemChanged(item) {
    this.itemsService.updateItem(item.id, item).then(() => {
      this.calculateItemsChecked();
    });
  }

  showItemDetail(item) {
    let modal = this.modalCtrl.create(ItemDetailsPage, {item: item, items: this.items});
    modal.present();
  }

  showCategoryDetail(category) {
    let modal = this.modalCtrl.create(CategoryDetailsPage, {category: category});
    modal.present();
  }

  calculateItemsChecked() {
    let counter = 0;
    if (this.items) {
      for (let item of this.items) {
        if (item.checked === true) {
          counter++;
        }
      }
    }
    this.itemsChecked = counter;
  }

  getNumberItemsCheckedPerCategory(category) {
    let counter = 0;
    for (let i = 0; i < this.items.length; i++) {
      if (this.items[i].categoryId === category.id && this.items[i].checked) {
        counter++;
      }
    }

    return counter;
  }

  getNumberItemsPerCategory(category) {
    let counter = 0;
    for (let i = 0; i < this.items.length; i++) {
      if (this.items[i].categoryId === category.id) {
        counter++;
      }
    }

    return counter;
  }

  toggleCategory(category) {
    category.hide = !category.hide;
    this.categoriesService.updateCategory(category.id, category);
  }

  private refreshItemsList(items: any): void {
    this.items = items;
    this.calculateItemsChecked();
  }

  private refreshCategoriesList(categories: any): void {
    this.categories = categories;
    this.calculateItemsChecked();
  }
}
