import {Component, NgZone} from '@angular/core';
import {NavParams, ViewController} from 'ionic-angular';
import {ItemsService} from '../../items/items-list/items-list.service';
import {TranslateService} from "ng2-translate";

@Component({
  selector: 'page-item-detail',
  templateUrl: 'item-detail.component.html'
})

export class DetailsPage {
  public categories = [];
  public item: any = {};
  public isNew = true;
  public action = 'add';

  constructor(private viewCtrl: ViewController,
              private navParams: NavParams,
              private zone: NgZone,
              private itemsService: ItemsService,
              private translateService: TranslateService) {

    this.itemsService.getAllCategories()
      .then(data => {
        this.zone.run(() => {
          this.categories = data;
        });
      });

    let editItem = this.navParams.get('item');

    if (editItem) {
      this.item = editItem;
      this.isNew = false;
      this.action = 'edit';
    }

    this.translateService.get(this.item.id).subscribe((text: string) => {
      this.item.id = text;
    })
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
