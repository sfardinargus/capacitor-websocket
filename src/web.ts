// npm i socket.io-client

import { WebPlugin } from '@capacitor/core';
import { io } from 'socket.io-client';

import type { WebsocketPlugin } from './definitions';

export class WebsocketWeb extends WebPlugin implements WebsocketPlugin {
  private socket: any;

  async connect(options: { url: string, socketConfig?: any }): Promise<void> {
      this.socket = io(options.url, options.socketConfig);
    return new Promise((resolve, reject) => {
      this.socket?.on('connect', () => {
        resolve();
      });
      this.socket?.on('connect_error', (error: any) => {
        reject(error);
      });
    });
  }

  async disconnect(): Promise<void> {
    return new Promise(resolve => {
      if (this.socket) {
        this.socket.disconnect();
        this.socket = null;
      }
      resolve();
    });
  }

  async on(event: string, callback: (data: any) => void): Promise<void> {
    if (this.socket) {
      this.socket.on(event, callback);
    } else {
      throw new Error('Socket is not connected');
    }
  }

  async emit(event: string, data: any): Promise<void> {
    if (this.socket) {
      this.socket.emit(event, data);
    } else {
      throw new Error('Socket is not connected');
    }
  }
}
