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

  constructor(private viewCtrl: ViewController,
              private navParams: NavParams,
              private itemsService: ItemsService) {

    let editItem = this.navParams.get('item');

    if (editItem) {
      this.item = editItem;
      this.isNew = false;
      this.action = 'Edit';
    }
  }

  save() {
    if (this.isNew) {
      this.itemsService.addItem(this.item)
        .catch(console.error.bind(console));
    } else {
      this.itemsService.updateItem(this.item);
    }

    this.dismiss();
  }

  cancel() {
    this.dismiss();
  }

  deleteItem() {
    this.itemsService.deleteItem(this.item);

    this.dismiss();
  }

  dismiss() {
    this.viewCtrl.dismiss(this.item);
  }
}
