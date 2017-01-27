import {Component} from '@angular/core';
import {NavController, Platform} from 'ionic-angular';
import {SQLite} from "ionic-native";

@Component({
  selector: 'page-home',
  templateUrl: 'home.html'
})
export class HomePage {

  public database: SQLite;
  public people: Array<Object>;

  constructor(private navController: NavController, private platform: Platform) {
    this.platform.ready().then(() => {
      this.database = new SQLite();
      this.database.openDatabase({name: "data.db", location: "default"}).then(() => {
        this.refresh();
      }, (error) => {
        console.log("ERROR: ", error);
      });
    });
  }

  public add() {
    this.database.executeSql("INSERT INTO people (firstname, lastname) VALUES ('Nic', 'Raboy')", []).then((data) => {
      console.log("INSERTED: " + JSON.stringify(data));
    }, (error) => {
      console.log("ERROR: " + JSON.stringify(error.err));
    });
  }

  public refresh() {
    this.database.executeSql("SELECT * FROM people", []).then((data) => {
      this.people = [];
      if (data.rows.length > 0) {
        for (let i = 0; i < data.rows.length; i++) {
          this.people.push({firstname: data.rows.item(i).firstname, lastname: data.rows.item(i).lastname});
        }
      }
    }, (error) => {
      console.log("ERROR: " + JSON.stringify(error));
    });
  }

}
