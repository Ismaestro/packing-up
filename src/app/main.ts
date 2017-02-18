import {enableProdMode} from '@angular/core';
import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';

import {AppModule} from './app.module';

enableProdMode();

platformBrowserDynamic().bootstrapModule(AppModule).then(() => {
  document.getElementById('loading').style.display = 'none';
});
