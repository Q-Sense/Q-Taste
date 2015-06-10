

#include <SPI.h>
#include <Ethernet.h>
#include <SoftwareSerial.h>

SoftwareSerial mySerial(0, 1); // RX, TX
char h[10];
int i=0;
int incomingByte;

// Enter a MAC address and IP address for your controller below.
// The IP address will be dependent on your local network:
byte mac[] = {
  0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED
};
IPAddress ip(192, 168, 1, 177);


// Initialize the Ethernet server library
// with the IP address and port you want to use
// (port 80 is default for HTTP):
EthernetServer server(80);

void setup() {
  // Open serial communications and wait for port to open:
 // Serial.begin(9600);
  while (!Serial) {
    ; // wait for serial port to connect. Needed for Leonardo only
  }


  // start the Ethernet connection and the server:
  Ethernet.begin(mac, ip);
  server.begin();
 // Serial.print("server is at ");
 // Serial.println(Ethernet.localIP());
// set the data rate for the SoftwareSerial port
  mySerial.begin(4800);
  pinMode(8, OUTPUT);
    pinMode(7, OUTPUT);

}


void loop() {
  // listen for incoming clients
  EthernetClient client = server.available();
   digitalWrite(8, LOW);
   digitalWrite(7, LOW);
        
        if (client) {
   // Serial.println("new client");
    // an http request ends with a blank line
    boolean currentLineIsBlank = true;
    while (client.connected()) {
      if (client.available()) {
        char c = client.read();
     //   Serial.write(c);
        // if you've gotten to the end of the line (received a newline
        // character) and the line is blank, the http request has ended,
        // so you can send a reply
        if (c == '\n' && currentLineIsBlank) {
          // send a standard http response header
          digitalWrite(7, HIGH);
          client.println("HTTP/1.1 200 OK");
          client.println("Content-Type: text/html");
          client.println("Connection: close");  // the connection will be closed after completion of the response
          client.println("Refresh: 5");  // refresh the page automatically every 5 sec
          client.println();
          client.println("<!DOCTYPE HTML>");
          client.println("<html>");
          // output the value of each analog input pin
        
             if (mySerial.available()>0){
                incomingByte=mySerial.read();
                mySerial.readBytes(h, 10);
           
                    if (incomingByte==10){
                        digitalWrite(8, HIGH);
                        client.print("read tag is");
                        client.println(h);
                        }
                        else{
                        client.println(incomingByte);
                         }
                }
                else{
                  client.println("No communication available");
                  }
            client.println("</html>");
          break;
        }
        if (c == '\n') {
          // you're starting a new line
          currentLineIsBlank = true;
        }
        else if (c != '\r') {
          // you've gotten a character on the current line
          currentLineIsBlank = false;
        }
      }
    
    // give the web browser time to receive the data
    delay(1);
    // close the connection:
    client.stop();
//    Serial.println("client disconnected");
  }
   }
  
}

