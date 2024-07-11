export interface WebsocketPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
