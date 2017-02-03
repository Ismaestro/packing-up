import {Component} from "@angular/core";
import {ModalController, NavController} from 'ionic-angular';
import {DetailsPage} from '../item-detail/item-detail.component';

@Component({
  selector: 'page-home',
  templateUrl: 'home.component.html'
})

export class HomePage {

  constructor(private nav: NavController,
              private modalCtrl: ModalController) {
  }

  showDetail(item) {
    let modal = this.modalCtrl.create(DetailsPage, {item: item});
    modal.present();
  }
}
