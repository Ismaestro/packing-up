import {Component} from "@angular/core";
import {ModalController} from 'ionic-angular';
import {AddElementPage} from "../add-element/add-element.component";

@Component({
  selector: 'page-home',
  templateUrl: 'home.component.html'
})

export class HomePage {

  constructor(private modalCtrl: ModalController) {
  }

  showAddElement() {
    let modal = this.modalCtrl.create(AddElementPage);
    modal.present();
  }
}
