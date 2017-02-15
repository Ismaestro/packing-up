import {Component} from '@angular/core';
import {NavParams, ViewController} from 'ionic-angular';
import {ItemsService} from '../../shared/services/items.service';
import {CategoriesService} from '../../shared/services/categories.service';

@Component({
  selector: 'page-item-detail',
  templateUrl: 'item-detail.component.html'
})

export class ItemDetailsPage {
  public categories = [];
  public items;
  public item: any = {};
  public isNew = true;
  public action = 'add';

  constructor(private viewCtrl: ViewController,
              private navParams: NavParams,
              private categoriesService: CategoriesService,
              private itemsService: ItemsService) {
    this.categoriesService.getAll()
      .then(categories => {
        this.categories = categories;
      });

    let editItem = this.navParams.get('item');

    if (editItem) {
      this.item = editItem;
      this.isNew = false;
      this.action = 'edit';
    } else {
      this.item = {};
    }
  }

  categorySelected(categoryId) {
    this.item.categoryId = categoryId;
  }

  save(name) {
    let oldId = this.item.id;
    this.item.id = name;

    if (this.isNew) {
      this.itemsService.addItem(this.item).then(() => {
        this.dismiss();
      });
    } else {
      this.itemsService.updateItem(oldId, this.item, true).then(() => {
        this.dismiss();
      });
    }
  }

  deleteItem() {
    this.itemsService.deleteItem(this.item).then(() => {
      this.dismiss();
    });
  }

  cancel() {
    this.viewCtrl.dismiss();
  }

  dismiss() {
    this.viewCtrl.dismiss().then(() => {
    });
  }
}
