import {Component, NgZone} from "@angular/core";
import {ModalController, NavController, Platform} from 'ionic-angular';
import {ItemsService} from './items-list.service';
import {DetailsPage} from '../../pages/item-detail/item-detail.component';

@Component({
  selector: 'items-list',
  templateUrl: 'items-list.component.html'
})
export class ItemsList {
  public items = [];
  public categories = ['A','B'];
  public itemsChecked = 0;

  constructor(private itemsService: ItemsService,
              private nav: NavController,
              private platform: Platform,
              private zone: NgZone,
              private modalCtrl: ModalController) {
    this.platform.ready().then(() => {

      this.itemsService.loadInitData().then(() => {
        this.itemsService.getAll()
          .then(data => {
            this.zone.run(() => {
              this.items = data;
              this.calculateItemsChecked();
            });
          });
      });
    });
  }

  itemChanged(item) {
    console.log("ENTRO");
    this.itemsService.updateItem(item);
    this.calculateItemsChecked(item._id);
  }

  showDetail(item) {
    let modal = this.modalCtrl.create(DetailsPage, {item: item});
    modal.present();
  }

  calculateItemsChecked(itemId?) {
    let copy = Object.assign({}, this.items);

    let counter = 0;
    for (let item of copy) {
      if (item.checked) {
        counter++;
      }
    }

    this.itemsChecked = counter;
  }
}
