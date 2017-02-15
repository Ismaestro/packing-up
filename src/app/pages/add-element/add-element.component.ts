import {Component} from '@angular/core';
import {ViewController, ModalController} from 'ionic-angular';
import {ItemDetailsPage} from "../item-detail/item-detail.component";
import {CategoryDetailsPage} from "../category-detail/category-detail.component";

@Component({
  selector: 'page-add-element',
  templateUrl: 'add-element.component.html'
})

export class AddElementPage {

  constructor(private viewCtrl: ViewController, private modalCtrl: ModalController) {
  }

  newCategory() {
    this.dismiss();
    let modal = this.modalCtrl.create(CategoryDetailsPage);
    modal.present();
  }

  newItem() {
    this.dismiss();
    let modal = this.modalCtrl.create(ItemDetailsPage);
    modal.present();
  }

  cancel() {
    this.dismiss();
  }

  dismiss() {
    this.viewCtrl.dismiss();
  }
}
