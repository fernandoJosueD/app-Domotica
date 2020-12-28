import RPi.GPIO as GPIO
import sys
from time import sleep
import signal
from gpiozero import LED, Button

from threading import Thread
import firebase_admin
from firebase_admin import credentials
from firebase_admin import db

LED = LED(17)
leda = 20
ledb = 21
ledc = 27
lede = 22


GPIO.setmode(GPIO.BCM)
GPIO.setup(leda, GPIO.OUT)
GPIO.setup(ledb, GPIO.OUT)
GPIO.setup(ledc, GPIO.OUT)
GPIO.setup(lede, GPIO.OUT)


def relay_on(pin):
    GPIO.output(pin, GPIO.HIGH)  
    
def relay_off(pin):
    GPIO.output(pin, GPIO.LOW)

PAHT_CRED = '/home/pi/iot/cred.json'
URL_DB = 'https://opcion2application.firebaseio.com/'
REF_HOME = 'home'
REF_LUCES = 'luces'
REF_LUZ_SALA = 'luz_sala'
REF_LUCESB = 'lucesB'
REF_LUCESC = 'lucesC'
REF_LUCESD = 'lucesD'
REF_LUCESE = 'lucesE'
REF_LUZ_COMEDOR = 'luz_comedor'
REF_LUZ_DORMITORIO = 'luz_dormitorio'
REF_LUZ_LAVANDERIA = 'luz_lavanderia'
REF_LUZ_AREAVERDE = 'luz_areaverde'



class IOT():

    def __init__(self):
        cred = credentials.Certificate(PAHT_CRED)
        firebase_admin.initialize_app(cred, {
            'databaseURL': URL_DB
        })

        self.refHome = db.reference(REF_HOME)
        
       # self.estructuraInicialDB() # solo ejecutar la primera vez

        self.refLuces = self.refHome.child(REF_LUCES)
        self.refLuzSala = self.refLuces.child(REF_LUZ_SALA)
        
        self.refLucesb = self.refHome.child(REF_LUCESB)
        self.refLuzComedor = self.refLucesb.child(REF_LUZ_COMEDOR)
        
        self.refLucesc = self.refHome.child(REF_LUCESC)
        self.refLuzDormitorio = self.refLucesc.child(REF_LUZ_DORMITORIO)
        
        self.refLucesd = self.refHome.child(REF_LUCESD)
        self.refLuzLavanderia = self.refLucesd.child(REF_LUZ_LAVANDERIA)
        
        self.refLucese = self.refHome.child(REF_LUCESE)
        self.refLuzAreaverde = self.refLucese.child(REF_LUZ_AREAVERDE)
     #*************************************************************************   

        def relay_on(pin):
            GPIO.output(pin, GPIO.HIGH)  
            
        def relay_off(pin):
            GPIO.output(pin, GPIO.LOW)       

    def estructuraInicialDB(self):
        self.refHome.set({
            'luces': {
                'luz_sala':True,
                'luz_sala2':True
            },
            'lucesC':{
                'luz_dormitorio':True,
                'luz_dormitorio2':True
            },
            'lucesB':{
                'luz_comedor':True,
                'luz_comedor2':True
                },
            'lucesD':{
                'luz_lavanderia':True,
                'luz_lavanderia2':True
                }
            ,
            'lucesE':{
                'luz_areaverde':True,
                'luz_areaverde2':True
                }
        })
    #********** CONTROL DE RELAY*********************
    def ledControlGPIO(self, estado):
        if estado:
            LED.on()
            print('LED OFF')
        else:
            LED.off()
            print('LED ON')
#-----------------------------------------------------
    def ledControlGPIOB(self, estadob):
        if estadob:
            relay_on(leda)
            print('LED OFF')
        else:
            relay_off(leda)
            print('LED ONN')
#------------------------------------------------------
    def ledControlGPIOC(self, estadoc):
        if estadoc:
            relay_on(ledb)
            print('LED OFF')
        else:
            relay_off(ledb)
            print('LED ONN')
#---------------------------------------------------------
    def ledControlGPIOD(self, estadod):
        if estadod:
            relay_on(ledc)
            print('LED OFF')
        else:
            relay_off(ledc)
            print('LED ONN')
#-------------------------------------------------------
    def ledControlGPIOE(self, estadoe):
        if estadoe:
            relay_on(lede)
            print('LED OFF')
        else:
            relay_off(lede)
            print('LED ONN')
#-------------------------------------------------------

    def lucesStart(self):

        E, i = [], 0

        estado_anterior = self.refLuzSala.get()
        self.ledControlGPIO(estado_anterior)

        E.append(estado_anterior)

        while True:
          estado_actual = self.refLuzSala.get()
          E.append(estado_actual)

          if E[i] != E[-1]:
              self.ledControlGPIO(estado_actual)

          del E[0]
          i = i + i
          sleep(0.4)
          
#----------------------------------------------------------------------
    def lucesStartB(self):

        E, i = [], 0

        estadob_anterior = self.refLuzComedor.get()
        self.ledControlGPIOB(estadob_anterior)

        E.append(estadob_anterior)

        while True:
          estadob_actual = self.refLuzComedor.get()
          E.append(estadob_actual)

          if E[i] != E[-1]:
              self.ledControlGPIOB(estadob_actual)

          del E[0]
          i = i + i
          sleep(0.4)
#--------------------------------------------------------------------
    
    def lucesStartC(self):

        E, i = [], 0

        estadoc_anterior = self.refLuzDormitorio.get()
        self.ledControlGPIOC(estadoc_anterior)

        E.append(estadoc_anterior)

        while True:
          estadoc_actual = self.refLuzDormitorio.get()
          E.append(estadoc_actual)

          if E[i] != E[-1]:
              self.ledControlGPIOC(estadoc_actual)

          del E[0]
          i = i + i
          sleep(0.4)
#-------------------------------------------------------------
    def lucesStartD(self):

        E, i = [], 0

        estadod_anterior = self.refLuzLavanderia.get()
        self.ledControlGPIOD(estadod_anterior)

        E.append(estadod_anterior)

        while True:
          estadod_actual = self.refLuzLavanderia.get()
          E.append(estadod_actual)

          if E[i] != E[-1]:
              self.ledControlGPIOD(estadod_actual)

          del E[0]
          i = i + i
          sleep(0.4)
#-------------------------------------------------------------
    def lucesStartE(self):

        E, i = [], 0

        estadoe_anterior = self.refLuzAreaverde.get()
        self.ledControlGPIOE(estadoe_anterior)

        E.append(estadoe_anterior)

        while True:
          estadoe_actual = self.refLuzAreaverde.get()
          E.append(estadoe_actual)

          if E[i] != E[-1]:
              self.ledControlGPIOE(estadoe_actual)

          del E[0]
          i = i + i
          sleep(0.4)
#----------------------------------------------------
          


print ('START !')
iot = IOT()

subproceso_led = Thread(target=iot.lucesStart)
subproceso_led.daemon = True
subproceso_led.start()

subproceso_ledB = Thread(target=iot.lucesStartB)
subproceso_ledB.daemon = True
subproceso_ledB.start()

subproceso_ledC = Thread(target=iot.lucesStartC)
subproceso_ledC.daemon = True
subproceso_ledC.start()

subproceso_ledD = Thread(target=iot.lucesStartD)
subproceso_ledD.daemon = True
subproceso_ledD.start()

subproceso_ledE = Thread(target=iot.lucesStartE)
subproceso_ledE.daemon = True
subproceso_ledE.start()


signal.pause()
