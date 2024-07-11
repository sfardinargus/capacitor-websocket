import { WebPlugin } from '@capacitor/core';

import type { WebsocketPlugin } from './definitions';

export class WebsocketWeb extends WebPlugin implements WebsocketPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
