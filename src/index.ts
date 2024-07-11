import { registerPlugin } from '@capacitor/core';

import type { WebsocketPlugin } from './definitions';

const Websocket = registerPlugin<WebsocketPlugin>('Websocket', {
  web: () => import('./web').then(m => new m.WebsocketWeb()),
});

export * from './definitions';
export { Websocket };
