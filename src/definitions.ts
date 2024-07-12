export interface WebsocketPlugin {
  connect(options: { url: string, socketConfig?: any }): Promise<void>;
  disconnect(): Promise<void>;
  on(event: string, callback: (data: any) => void): Promise<void>;
  emit(event: string, ...args: any[]): Promise<void>;
}
