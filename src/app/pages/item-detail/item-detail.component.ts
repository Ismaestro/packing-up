import {Component} from '@angular/core';
import {NavParams, ViewController} from 'ionic-angular';
import {ItemsService} from '../../items/items-list/items-list.service';

@Component({
  selector: 'page-item-detail',
  templateUrl: 'item-detail.component.html'
})

export class DetailsPage {
  public item: any = {};
  public isNew = true;
  public action = 'Add';
  public isoDate = '';

  constructor(private viewCtrl: ViewController,
              private navParams: NavParams,
              private itemsService: ItemsService) {

    let editItem = this.navParams.get('item');

    if (editItem) {
      this.item = editItem;
      this.isNew = false;
      this.action = 'Edit';
      this.isoDate = this.item.Date.toISOString().slice(0, 10);
    }
  }

  save() {
    this.item.Date = new Date(this.isoDate);

    if (this.isNew) {
      this.itemsService.addItem(this.item)
        .catch(console.error.bind(console));
    } else {
      this.itemsService.updateItem(this.item)
        .catch(console.error.bind(console));
    }

    this.dismiss();
  }

  deleteItem() {
    this.itemsService.deleteItem(this.item)
      .catch(console.error.bind(console));

    this.dismiss();
  }

  dismiss() {
    this.viewCtrl.dismiss(this.item);
  }
}
