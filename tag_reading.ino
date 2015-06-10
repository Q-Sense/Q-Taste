
#include <SoftwareSerial.h>
#include <PN532.h>
#include <I2C.h>
#include <SPI.h>

#define SS 10
  #if defined(__AVR_ATmega1280__) || defined (__AVR_ATmega2560__)
   #define MISO 50
   #define MOSI 51
   #define SCK 52
   
#else
   #define MISO 12
   #define MOSI 11
   #define SCK 13
  #endif



SoftwareSerial mySerial(0, 1);
PN532 nfc(SCK, MISO, MOSI, SS);
char c[12];


void setup() {
  // put your setup code here, to run once:
 Serial.begin(4800);
   nfc.begin();
   uint32_t versiondata = nfc.getFirmwareVersion();
   if (! versiondata) {
     Serial.print("Didn't find PN53x board");
     while (1); // halt
   }
 // Got ok data, print it out!
 Serial.print("Found chip PN5"); Serial.println((versiondata>>24) & 0xFF, HEX);

 Serial.print("Firmware ver. "); Serial.print
((versiondata>>16) & 0xFF, DEC);
 Serial.print('.'); Serial.println((versiondata>>8) &
0xFF, DEC);
 Serial.print("Supports "); Serial.println(versiondata &
0xFF, HEX);
 // configure board to read RFID tags and cards
 nfc.SAMConfig();
 
  // set the data rate for the SoftwareSerial port
  mySerial.begin(4800);
  pinMode(8, OUTPUT);


}

void loop() {
  // put your main code here, to run repeatedly:
 uint32_t id,myid;

digitalWrite(8, LOW); 
 // look for MiFare type cards
 delay(1000);

 id = nfc.readPassiveTargetID(PN532_MIFARE_ISO14443A);
  delay(1000);

  if (id != 0) 
   {
     Serial.print(" Codigo");
     Serial.print("\n Read card #"); Serial.println(id);
     sprintf(c, "%lu", id);
     

      
      int bytesent= mySerial.write(c, 10);
      if(bytesent==10){
        digitalWrite(8, HIGH);
        }
      mySerial.flush();      
      
      delay(3000);
   }
 
 
  delay(1000);

}
