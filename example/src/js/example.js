import { Websocket } from 'capacitor-websocket';

window.testEcho = () => {
    const inputValue = document.getElementById("echoInput").value;
    Websocket.echo({ value: inputValue })
}
