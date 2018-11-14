import fisica.*;
import ddf.minim.*;

Minim minim;
AudioPlayer playSong1;
AudioPlayer startSound;
AudioPlayer walkSound;
AudioPlayer jumpSound;
AudioPlayer hurtSound;
AudioPlayer pinksquishSound;
AudioPlayer coinSound;
AudioPlayer keySound;
AudioPlayer teleportSound;
AudioPlayer enemySound;
AudioPlayer shellSound;
AudioPlayer endSound;
AudioPlayer waterSound;
AudioPlayer lavahurtSound;
AudioInput input;

int x = 0;
int y = 0;
PImage trigger;
PImage map0;
PImage map1;
PImage rockmid;
PImage rockcenter;
PImage icetile;
PImage hoverblock;
PImage hoverback;
PImage eleback;
PImage bluegrab;
PImage bluegrabLocked;
PImage crate1;
PImage crate2;
PImage logbridge;
PImage logbroke;
PImage watersurface;
PImage water;
PImage lavasurface;
PImage lava;
PImage spike1;
PImage goldcoin;
PImage bluekey;
PImage leftarrow;
PImage rightarrow;
PImage bottomdoor;
PImage topdoor;
PImage teleporter1;
PImage telexit1;
PImage idleSubject;
PImage jumpSubject;
PImage badpink1;
PImage deadpink;
PImage badgreen1;
PImage alivesnail1;
PImage deadsnail1;
PImage greensp;
PImage background;
PImage healthface;
PImage jealthface;
PImage fullheart;
PImage halfheart;
PImage emptyheart;
PImage emptybluekeyhud;
PImage bluekeyhud;
PImage coinhud;
PImage[] idle;
PImage[] run;
PImage[] currentAction; 
PImage[] pinkmove;
PFont fontMain;

boolean leftkey, rightkey, upkey, flag, haskey, faceRight, replay;
String baseBlock, surface, player, spikeI, gold, texit;
ArrayList <FBody> oddBodies;
ArrayList <FBody> allBodies;
float topSpeed = 800;
float viscosity = 1500;
float pinkSpeed = 15;
float direction = -1;
int frame = 0;
int framePink = 0;
int health = 6;
int coinscore = 0;
int playCount = 0;

FWorld world;
FBox blueGrab, bkey, subject, pink, green, snail, crate;

int mode;
final int INTRO = 0;
final int PLAYING1 = 1;
final int PLAYING2 = 2;
final int PAUSED = 3;
final int END = 4;
final int COMPLETE = 5;

//===================================================================================

void setup() {
  trigger = loadImage("Transparent Layer.png");
  trigger.resize(50, 50);
  map0 = loadImage("Platform World Intro.png");
  map1 = loadImage("Platform World 1.png");
  rockmid = loadImage("rockMid.png");
  rockmid.resize(50, 50);
  rockcenter = loadImage("rockCenter.png");
  rockcenter.resize(50, 50);
  icetile = loadImage("snowMid.png");
  icetile.resize(50, 50);
  hoverblock = loadImage("Glow-Block-Teal-1.png");
  hoverblock.resize(50, 50);
  hoverback = loadImage("Elevator-Back-1.png");
  eleback = loadImage("stoneCenter.png");
  eleback.resize(50, 50);
  bluegrab = loadImage("GrabBox-Blue.png");
  bluegrab.resize(50, 50);
  bluegrabLocked = loadImage("GrabBox-Locked-Blue-Redline.png");
  bluegrabLocked.resize(50, 50);
  crate1 = loadImage("Crate-1.png");
  crate1.resize(50, 50);
  crate2 = loadImage("Crate-2.png");
  crate2.resize(50, 50);
  logbridge = loadImage("Log-Bridge-1.png");
  logbroke = loadImage("Broken-Log-Bridge-1.png");
  watersurface = loadImage("Water-Surface-1.png");
  water = loadImage("Water-1.png");
  lavasurface = loadImage("Lava-Surface-1.png");
  lava = loadImage("Lava-1.png");
  spike1 = loadImage("Platformer-Spike.png");
  spike1.resize(50, 25);
  goldcoin = loadImage("Gold-Coin.png");
  goldcoin.resize(30, 30);
  bluekey = loadImage("Blue-Key.png");
  leftarrow = loadImage("Left-Arrow-1.png");
  leftarrow.resize(50, 50);
  rightarrow = loadImage("Right-Arrow-1.png");
  rightarrow.resize(50, 50);
  bottomdoor = loadImage("Bottom-Door-1.png");
  bottomdoor.resize(50, 50);
  topdoor = loadImage("Top-Door-1.png");
  topdoor.resize(50, 50);
  teleporter1 = loadImage("Teleporter-1.png");
  teleporter1.resize(50, 50);
  telexit1 = loadImage("Teleport Exit.png");
  telexit1.resize(80, 80);
  idleSubject = loadImage("p1_walk08.png");
  idleSubject.resize(48, 67);
  jumpSubject = loadImage("Character-Move-Right-1.png");
  jumpSubject.resize(48, 67);
  badpink1 = loadImage("Bad-Pink-1.png");
  badpink1.resize(42, 28);
  deadpink = loadImage("Bad-Pink-Dead.png");
  deadpink.resize(42, 8);
  badgreen1 = loadImage("Bad-Green-1.png");
  alivesnail1 = loadImage("snailWalk1.png");
  alivesnail1.resize(50, 29);
  deadsnail1 = loadImage("snailShell.png");
  deadsnail1.resize(33, 27);
  greensp = loadImage("Green Spike 1.png");
  greensp.resize(30, 30);
  healthface = loadImage("hud_p1.png");
  jealthface = loadImage("hud_p1jump.png");
  fullheart = loadImage("hud_heartFull.png");
  halfheart = loadImage("hud_heartHalf.png");
  emptyheart = loadImage("hud_heartEmpty.png");
  emptybluekeyhud = loadImage("hud_keyBlue_disabled.png");
  bluekeyhud = loadImage("hud_keyBlue.png");
  coinhud = loadImage("hud_coins.png");
  background = loadImage("platformer-background.png");
  background.resize(3500, 4200);
  fontMain = loadFont("ArialNarrow-Bold-48.vlw");
  size(800, 500);

  idle = new PImage[2];
  idle[0] = loadImage ("p1_walk08.png");
  idle[0].resize(48, 67);
  idle[1] = loadImage ("p1_walk08.png");
  idle[1].resize(48, 67);

  run = new PImage[11];
  run[0] = loadImage ("p1_walk01.png");
  run[0].resize(52, 71);
  run[1] = loadImage ("p1_walk02.png");
  run[1].resize(47, 67);
  run[2] = loadImage ("p1_walk03.png");
  run[2].resize(50, 69);
  run[3] = loadImage ("p1_walk04.png");
  run[3].resize(50, 69);
  run[4] = loadImage ("p1_walk05.png");
  run[4].resize(50, 69);
  run[5] = loadImage ("p1_walk06.png");
  run[5].resize(50, 69);
  run[6] = loadImage ("p1_walk07.png");
  run[6].resize(50, 69);
  run[7] = loadImage ("p1_walk08.png");
  run[7].resize(48, 67);
  run[8] = loadImage ("p1_walk09.png");
  run[8].resize(48, 67);
  run[9] = loadImage ("p1_walk10.png");
  run[9].resize(49, 68);
  run[10] = loadImage ("p1_walk11.png");
  run[10].resize(49, 68);

  pinkmove = new PImage[2];
  pinkmove[0] = loadImage("Bad-Pink-1.png");
  pinkmove[0].resize(42, 28);
  pinkmove[1] = loadImage("Bad-Pink-2.png");
  pinkmove[1].resize(42, 28);

  minim = new Minim(this);
  playSong1 = minim.loadFile("soundtrack1.mp3");
  walkSound = minim.loadFile("footstep.mp3");
  jumpSound = minim.loadFile("jump.mp3");
  hurtSound = minim.loadFile("hurt.mp3");
  coinSound = minim.loadFile("coin.mp3");
  keySound = minim.loadFile("key.mp3");
  pinksquishSound = minim.loadFile("pinksquish.mp3");
  shellSound = minim.loadFile("shell.mp3");
  teleportSound = minim.loadFile("teleport.mp3");
  lavahurtSound = minim.loadFile("lavaburn.mp3");
  startSound = minim.loadFile("start.mp3");
  endSound = minim.loadFile("end.mp3");

  leftkey = upkey = rightkey = haskey = replay = false;
  faceRight = true;

  Fisica.init(this);
  world = new FWorld(0, 0, 100000, 100000);
  oddBodies = new ArrayList<FBody>();
  allBodies = new ArrayList<FBody>();
  //enemies = new ArrayList<PinkThing>();
  world.setGravity(0, 2900);
  x=0;
  y=0;
  mode=INTRO;
  //currentAction = idle;
  flag = false;
}

//===================================================================================

void draw() {

  if (mode == INTRO) {
    introLevel();
  } else if (mode == PLAYING2) {
    playLevel2();
  } else if (mode == PAUSED) {
    pauseLevel2();
  } else if (mode == END) {
    endLevel();
  } else if (mode == COMPLETE) {
    finishLevel();
  }
}

//===================================================================================
void introLevel() {
  pushMatrix();
  image(background, 0, 0);
  world.step();
  world.draw();
  popMatrix();
  textFont(fontMain);
  textAlign(CENTER, CENTER);
  textSize(75);
  text("PRESS J TO START", width/2, height/2);
  if (keyPressed) {
    if (key == 'j') {
      mode = PLAYING2;
      flag = true;
    }
  }
}

//=========================================================================
void playLevel2() {
  if (flag) {
    while (y < map1.height) {
      color c = map1.get(x, y);
      makeBaseBlock(x, y, c);
      makeSurface(x, y, c);
      maketriggerSurface(x, y, c);
      makeIceBlock(x, y, c);
      makeHover(x, y, c);
      makeElevator(x, y, c);
      makeSpike(x, y, c);
      makeCoin(x, y, c);
      makeKey(x, y, c);
      makeSigns(x, y, c);
      makeGrabs(x, y, c);
      makeCrates(x, y, c);
      makeLBridge(x, y, c);
      makeDoor1(x, y, c);
      makeTeleporter(x, y, c);
      makeWater(x, y, c);
      makeLava(x, y, c);
      makeEnemies(x, y, c);
      handleProjectiles(x, y, c);
      x++;
      if (x > map1.width) {
        y++;
        x=0;
      }
    }
    playSong1.play();
    startSound.play();
    if (playCount == 0) {
      makeSubject();
    }
    flag = false;
  }
  
  currentAction = run;
  moveSubject();
  moveEnemies();
  pushMatrix();
  float tx = -subject.getX() + width/2;
  float ty = -subject.getY() + height/2 + 90;
  translate(tx, ty);
  image(background, 0, 0);
  textAppear();
  animateSubject();
  animateEnemies();
  world.step();
  world.draw();
  makeHUD();
  popMatrix();
  if (keyPressed) {
    if (key == 'p') {
      mode = PAUSED;
    }
  }
  handleOddBodies();
  if (health <= 0) {
    mode = END;
  }
}
//=========================================================================
void pauseLevel2() {
  if (flag) {
    while (y < map1.height) {
      color c = map1.get(x, y);
      makeBaseBlock(x, y, c);
      makeSurface(x, y, c);
      maketriggerSurface(x, y, c);
      makeIceBlock(x, y, c);
      makeHover(x, y, c);
      makeElevator(x, y, c);
      makeSpike(x, y, c);
      makeKey(x, y, c);
      makeSigns(x, y, c);
      makeGrabs(x, y, c);
      makeCrates(x, y, c);
      makeLBridge(x, y, c);
      makeDoor1(x, y, c);
      makeTeleporter(x, y, c);
      makeWater(x, y, c);
      makeLava(x, y, c);
      makeEnemies(x, y, c);
      ++x;
      if (x > map1.width) {
        ++y;
        x=0;
      }
    }
    flag = false;
  }
  
  playSong1.pause();
  pushMatrix();
  subject.setStatic(true);
  float tx = -subject.getX() + width/2;
  float ty = -subject.getY() + height/2 + 70;
  translate(tx, ty);
  image(background, 0, 0);
  world.step();
  world.draw();
  popMatrix();
  fill(0, 100);
  rect(0, 0, 800, 500);
  fill(255);
  textFont(fontMain);
  textAlign(CENTER, CENTER);
  textSize(75);
  text("PAUSED", width/2, height/2);
  text("L TO RESUME", width/2, height/2 + 200);
  if (keyPressed) {
    if (key == 'l') {
      mode = PLAYING2;
      playSong1.play();
      subject.setStatic(false);
    }
  }
}
//=========================================================================
void endLevel() {
  playSong1.pause();
  walkSound.pause();
  size(800, 500);
  background(0);
  fill(255);
  textFont(fontMain);
  textAlign(CENTER, CENTER);
  textSize(75);
  text("RIP", width/2, height/2);
  text("R TO RESTART", width/2, height*2/3 + 100);
  if (keyPressed) {
    if (key == 'r' || key == 'R') {
      startSound.rewind();
      playSong1.rewind();
      flag = true;
      mode = PLAYING2;
      playCount += 1;
      pink.setName("badpink");      
      snail.setName("badsnail");
      snail.attachImage(alivesnail1);
      snail.setSensor(false);
      subject.setPosition(600, 600);
      health = 6;
      coinscore = 0;
    }
  }
}

//=========================================================================
void finishLevel() {
  playSong1.pause();
  walkSound.pause();
  //endSound.play();
  size(800, 500);
  background(0);
  fill(255);
  textFont(fontMain);
  textAlign(CENTER, CENTER);
  textSize(35);
  text("CONGRATULATIONS YOU FINISHED THE LEVEL!", width/2, height/2);
  textSize(25);
  text("YOU COLLECTED " + coinscore + " COINS!", width/2, height/2 + 50);
  textSize(75);
  text("R TO PLAY AGAIN", width/2, height*2/3 + 100);
  if (keyPressed) {
    if (key == 'r' || key == 'R') {
      startSound.rewind();
      playSong1.rewind();
      flag = true;
      replay = true;
      mode = PLAYING2;
      playCount += 1;
      pink.setName("badpink");      
      snail.setName("badsnail");
      snail.attachImage(alivesnail1);
      snail.setSensor(false);
      subject.setPosition(600, 600);
      health = 6;
      coinscore = 0;
    }
  }
}

//=========================================================================

void textAppear() {
  float px = subject.getX();
  float py = subject.getY();
  if (py < 800 && px > 700 && px < 900) {
    fill(255);
    textFont(fontMain);
    textAlign(CENTER, CENTER);
    textSize(75);
    text("HELLO", width/2 + 400, height/2 + 300);
  }
  if (py < 1400 && px > 900 && px < 1400) {
    fill(255);
    textFont(fontMain);
    textAlign(CENTER, CENTER);
    textSize(75);
    text("USE ARROWS TO MOVE", width/2 + 700, height/2 + 300);
  }
  if (py < 1100 && px > 1300 && px < 1700) {
    fill(255);
    textFont(fontMain);
    textAlign(CENTER, CENTER);
    textSize(75);
    text("AVOID SPIKES", width/2 + 1100, height/2 + 400);
  }
  if (py < 1100 && px > 1800 && px < 2300) {
    fill(255);
    textFont(fontMain);
    textAlign(CENTER, CENTER);
    textSize(75);
    text("COLLECT COINS", width/2 + 1600, height/2 + 400);
  }
  if (py < 1200 && py > 950 && px > 600 && px < 720) {
    fill(255);
    textFont(fontMain);
    textAlign(CENTER, CENTER);
    textSize(50);
    text("WHAZAM", 730, 950);
  }
  if (py < 1300 && px > 2600 && px < 2800) {
    fill(255);
    textFont(fontMain);
    textAlign(CENTER, CENTER);
    textSize(20);
    text("PRESS E TO TELEPORT", width/2 + 2210, height/2 + 730);
  }
  if (py > 1600 && py < 2200 && px > 400 && px < 800) {
    fill(255);
    textFont(fontMain);
    textAlign(CENTER, CENTER);
    textSize(40);
    text("YOU CAN FLY WITH THIS", width/2 + 250, height/3 + 1850);
    textSize(20);
    text("JUMP ON ME", width/2 + 130, height/3 + 1930);
  }
  if (py > 2300 && px > 900 && px < 1700) {
    fill(255);
    textFont(fontMain);
    textAlign(CENTER, CENTER);
    textSize(50);
    text("PRESS E TO UNLOCK BOX AND JUMP ON IT", width/2 + 800, height/2 + 2300);
  }
  if (py > 2400 && px > 2500 && px < 2800) {
    fill(255);
    textFont(fontMain);
    textAlign(CENTER, CENTER);
    textSize(50);
    text("PRESS E TO FINISH", width/2 + 2000, height/2 + 2200);
  }
}
//=========================================================================

void makeHUD() {
  if (touchingGround()) {
    image(healthface, subject.getX() - 380, subject.getY() - 320, 40, 40);
  } else {
    image(jealthface, subject.getX() - 380, subject.getY() - 320, 40, 40);
  }
  if (health == 6) {
    image(fullheart, subject.getX() - 335, subject.getY() - 315, 30, 30);
    image(fullheart, subject.getX() - 300, subject.getY() - 315, 30, 30);
    image(fullheart, subject.getX() - 265, subject.getY() - 315, 30, 30);
  } else if (health == 5) {
    image(fullheart, subject.getX() - 335, subject.getY() - 315, 30, 30);
    image(fullheart, subject.getX() - 300, subject.getY() - 315, 30, 30);
    image(halfheart, subject.getX() - 265, subject.getY() - 315, 30, 30);
  } else if (health == 4) {
    image(fullheart, subject.getX() - 335, subject.getY() - 315, 30, 30);
    image(fullheart, subject.getX() - 300, subject.getY() - 315, 30, 30);
    image(emptyheart, subject.getX() - 265, subject.getY() - 315, 30, 30);
  } else if (health == 3) {
    image(fullheart, subject.getX() - 335, subject.getY() - 315, 30, 30);
    image(halfheart, subject.getX() - 300, subject.getY() - 315, 30, 30);
    image(emptyheart, subject.getX() - 265, subject.getY() - 315, 30, 30);
  } else if (health == 2) {
    image(fullheart, subject.getX() - 335, subject.getY() - 315, 30, 30);
    image(emptyheart, subject.getX() - 300, subject.getY() - 315, 30, 30);
    image(emptyheart, subject.getX() - 265, subject.getY() - 315, 30, 30);
  } else if (health == 1) {
    image(halfheart, subject.getX() - 335, subject.getY() - 315, 30, 30);
    image(emptyheart, subject.getX() - 300, subject.getY() - 315, 30, 30);
    image(emptyheart, subject.getX() - 265, subject.getY() - 315, 30, 30);
  } else if (health == 0) {
    image(emptyheart, subject.getX() - 335, subject.getY() - 315, 30, 30);
    image(emptyheart, subject.getX() - 300, subject.getY() - 315, 30, 30);
    image(emptyheart, subject.getX() - 265, subject.getY() - 315, 30, 30);
  }
  image(coinhud, subject.getX() + 250, subject.getY() - 315, 40, 40);
  fill(255);
  textFont(fontMain);
  textAlign(CENTER, CENTER);
  textSize(40);
  text("x", subject.getX() + 305, subject.getY() - 300);
  text(coinscore, subject.getX() + 330, subject.getY() - 295);
  image(emptybluekeyhud, subject.getX() - 380, subject.getY() + 100, 40, 40);
  if (haskey == true) {
    image(bluekeyhud, subject.getX() - 380, subject.getY() + 100, 40, 40);
  }
}

//=========================================================================

boolean touchingGround() {
  ArrayList<FContact> contacts = subject.getContacts();
  for (int i = 0; i < contacts.size(); i++) {
    FContact c = contacts.get(i); 
    if (c.contains("player", "rocksurface") || c.contains("player", "trigsurface")
      || c.contains("player", "blueGrab") || c.contains("player", "hoverground") 
      || c.contains("player", "elevate") || c.contains("player", "crate1") 
      || c.contains("player", "logbridges") || c.contains("player", "brokenlog")
      || c.contains("player", "pinkthing") || c.contains("player", "benign")) {
      return true;
    }
  }
  return false;
}

boolean touchingWater() {
  ArrayList<FContact> contacts = subject.getContacts();
  for (int i = 0; i < contacts.size(); i++) {
    FContact c = contacts.get(i); 
    if (c.contains("player", "H2O")) {
      return true;
    }
  }
  return false;
}

boolean elevatorActivate() {
  ArrayList<FContact> contacts = subject.getContacts();
  for (int i = 0; i < contacts.size(); i++) {
    FContact c = contacts.get(i); 
    if (c.contains("player", "uptube")) {
      return true;
    }
  }
  return false;
}

//=========================================================================

void makeBaseBlock(int x, int y, color c) {
  if (c == #7f7f7f) {
    FBox box = new FBox(50, 50);
    box.setPosition(x*50, y*50);
    box.setStatic(true);
    box.setGrabbable(false);
    box.setFriction(100);
    box.setName(baseBlock);
    box.attachImage(rockcenter);
    world.add(box);
    allBodies.add(box);
  }
}

//===================================================================================

void handleOddBodies() {
  int i = 0;
  float sy = subject.getY() + 34;
  float py = pink.getY() - 10;
  float sny = snail.getY() - 11;
  while (i < oddBodies.size()) {
    FBody b = oddBodies.get(i);
    
    if (b.getName().equals("COLLECT")) {
      coinscore = coinscore + 1;
      coinSound.play();
      world.remove(b);
      coinSound.rewind();
      b.setName("X_X");
    }
    
    if (b.getName().equals("COLLECTKEY")) {
      world.remove(b);
      keySound.play();
      keySound.rewind();
      b.setName("X_X");
    }
    
    if (keyPressed) {
      if (key == 'e' || key == 'E') {
        if (b.getName().equals("BLUELOCKTOUCH") && haskey) {
          blueGrab.attachImage(bluegrab);
          blueGrab.setName("blueGrab");
          haskey = false;
        }
      }
    }
    
    if (b.getName().equals("LONDONFALL")) {
      b.setStatic(false);
      b.adjustVelocity(0, 100);
    }
    if (b.getName().equals("crate") && sy <= b.getY() ) {
      b.setName("crate1");
    } else if (b.getName().equals("crate1") && sy > b.getY()) {
      b.setName("crate");
    }
    
    if (b.getName().equals("PINKCONTACT") && py < sy) {
      health = health - 2;
      hurtSound.play();
      hurtSound.rewind();
      subject.setForce(0, 300);
      b.setName("badpink");
    } else if (b.getName().equals("PINKCONTACT") && sy < py) {
      b.setStatic(true);
      pinksquishSound.play();
      b.setName("benign");
      pinksquishSound.rewind();
      b.attachImage(deadpink);
    }
    
    if (b.getName().equals("PINKUTURN")) {
      direction *= -1;
      b.setName("badpink");
    }
    
    if (b.getName().equals("SNAILCONTACT") && sny < sy) {
      println("below");
      health = health - 2;
      hurtSound.play();
      hurtSound.rewind();
      b.setName("badsnail");
    } else if (b.getName().equals("SNAILCONTACT") && sy < sny) {
      println("above");
      b.setStatic(false);
      pinksquishSound.play();
      b.setName("shell");
      pinksquishSound.rewind();
      b.attachImage(deadsnail1);
      b.setFriction(-50);
      b.setDensity(0.004);
      b.setRestitution(0.7);
      b.setRotatable(true);
    }
    
    if (b.getName().equals("SHELLCONTACT")) {
      shellSound.play();
      health = health - 1;
      hurtSound.play();
      hurtSound.rewind();
      snail.setName("safeshell");
      shellSound.rewind();
      snail.setSensor(true);
    }
    
    if (keyPressed) {
      if (key == 'e' || key == 'E') {
        if (b.getName().equals("TELEPORTING") ) {
          teleportSound.play();
          subject.setPosition(680, 1000);
          teleportSound.rewind();
          b.setName("teleporter");
        }
      }
    }
    
    if (keyPressed) {
      if (key == 'e' || key == 'E') {
        if (b.getName().equals("FINISHLEVEL") ) {
          mode = COMPLETE;
          b.setName("finishedlevel");
        }
      }
    }
    
    ++i;
  }
}

//===================================================================================

void contactStarted (FContact contact) {
  FBody other;
  float shvy = snail.getVelocityX();
  if ("player".equals(contact.getBody1().getName())) {
    other = contact.getBody2();
  } else {
    other = contact.getBody1();
  }

  if (contact.contains("player", "spikeI")) {
    hurtSound.play();
    hurtSound.rewind();
    health = health - 1;
    subject.setForce(0, 600);
  }
  if (contact.contains("player", "lava")) {
    lavahurtSound.play();
    hurtSound.play();
    hurtSound.rewind();
    mode = END;
    lavahurtSound.rewind();
  }
  if (contact.contains("player", "gold")) {
    other.setName("COLLECT");
  }
  if (contact.contains("player", "bkey")) {
    other.setName("COLLECTKEY");
    haskey = true;
  }
  if (contact.contains("player", "brokenlog")) {
    other.setName("LONDONFALL");
  }
  if (contact.contains("player", "badpink")) {
    other.setName("PINKCONTACT");
  }
  if (contact.contains("player", "badsnail")) {
    other.setName("SNAILCONTACT");
  }
  if (contact.contains("player", "shell") && shvy > 2) {
    other.setName("SHELLCONTACT");
  }
  if (contact.contains("badpink", "trigsurface")
    || contact.contains("badpink", "crate")) {
    pink.setName("PINKUTURN");
  }
}

//==================================================================================

void contactPersisted (FContact contact) {
  FBody other;
  if ("player".equals(contact.getBody1().getName())) {
    other = contact.getBody2();
  } else {
    other = contact.getBody1();
  }
  while (contact.contains("player", "teleporter")) {
    other.setName("TELEPORTING");
  } 
  while (contact.contains("player", "bottomdoor") || 
         contact.contains("player", "topdoor")) {
    other.setName("FINISHLEVEL");
  }
  while (contact.contains("player", "blueGrabLocked")) {
    other.setName("BLUELOCKTOUCH");
  }
}

//===================================================================================

void makeSurface(int x, int y, color c) {
  if (c == #000000) {
    FBox surface = new FBox(50, 50);
    surface.setPosition(x*50, y*50);
    surface.setStatic(true);
    surface.setFriction(100);
    surface.setGrabbable(false);
    surface.setName("rocksurface");
    surface.attachImage(rockmid);
    world.add(surface);
  }
}

//===================================================================================

void maketriggerSurface(int x, int y, color c) {
  if (c == #2d2d2d) {
    FBox surface = new FBox(50, 50);
    surface.setPosition(x*50, y*50);
    surface.setStatic(true);
    surface.setFriction(100);
    surface.setGrabbable(false);
    surface.setName("trigsurface");
    surface.attachImage(rockmid);
    world.add(surface);
    oddBodies.add(surface);
  }
}

//===================================================================================

void makeIceBlock(int x, int y, color c) {
  if (c == #3f48cc) {
    FBox ice = new FBox(50, 50);
    ice.setPosition(x*50, y*50);
    ice.setStatic(true);
    ice.setFriction(3);
    ice.setGrabbable(false);
    ice.setName("icetiles");
    ice.attachImage(icetile);
    world.add(ice);
  }
}

//===================================================================================

void makeHover(int x, int y, int c) {
  if (c == #05837c) {
    FBox hblock = new FBox(50, 50);
    hblock.setPosition(x*50, y*50);
    hblock.setStatic(true);
    hblock.setFriction(100);
    hblock.setRestitution(1);
    hblock.setGrabbable(false);
    hblock.setName("hoverground");
    hblock.attachImage(hoverblock);
    world.add(hblock);
  }
  if (c == #0ef5ea) {
    FBox hback = new FBox(50, 50);
    hback.setPosition(x*50, y*50 + 15);
    hback.setStatic(true);
    hback.setSensor(true);
    hback.setGrabbable(false);
    hback.setName("elevate");
    hback.attachImage(hoverback);
    world.add(hback);
    oddBodies.add(hback);
  }
}
//===================================================================================

void makeElevator(int x, int y, int c) {
  if (c == #b0ffff) {
    FBox eback = new FBox(50, 50);
    eback.setPosition(x*50, y*50 + 15);
    eback.setStatic(true);
    eback.setSensor(true);
    eback.setGrabbable(false);
    eback.setName("uptube");
    eback.attachImage(eleback);
    world.add(eback);
    oddBodies.add(eback);
  }
}

//===================================================================================

void makeGrabs(int x, int y, color c) {
  if (c == #304e6b) {
    blueGrab = new FBox(50, 50);
    blueGrab.setPosition(x*50, y*50);
    blueGrab.setStatic(true);
    blueGrab.setFriction(100);
    blueGrab.setGrabbable(false);
    blueGrab.setName("blueGrabLocked");
    blueGrab.attachImage(bluegrabLocked);
    world.add(blueGrab);
    oddBodies.add(blueGrab);
  }
}

//===================================================================================

void makeCrates(int x, int y, int c) {
  PImage img;
  float n = random(0, 1);
  if (n < 0.50) {
    img = crate1;
  } else {
    img = crate2;
  }
  if (c == #66402b) {
    FBox crate = new FBox(50, 50);
    crate.setPosition(x*50, y*50);
    crate.setStatic(false);
    crate.setFriction(30);
    crate.setDensity(0.5);
    crate.setGrabbable(true);
    crate.setName("crate");
    crate.attachImage(img);
    world.add(crate);
    oddBodies.add(crate);
  }
}
//===================================================================================

void makeLBridge(int x, int y, int c) {
  if (c == #400000) {
    FBox logb = new FBox(50, 16);
    logb.setPosition(x*50, y*50);
    logb.setStatic(true);
    logb.setFriction(100);
    logb.setDensity(3);
    logb.setGrabbable(false);
    logb.setName("logbridges");
    logb.attachImage(logbridge);
    world.add(logb);
    oddBodies.add(logb);
  }
  if (c == #a65300) {
    FBox logbro = new FBox(50, 16);
    logbro.setPosition(x*50, y*50);
    logbro.setStatic(true);
    logbro.setFriction(100);
    logbro.setDensity(3);
    logbro.setGrabbable(false);
    logbro.setName("brokenlog");
    logbro.attachImage(logbroke);
    world.add(logbro);
    oddBodies.add(logbro);
  }
}

//===================================================================================

void makeWater(int x, int y, color c) {
  if (c == #99d9ea) {
    FBox watersurf = new FBox(50, 50);
    watersurf.setPosition(x*50, y*50);
    watersurf.setStatic(true);
    watersurf.setSensor(true);
    watersurf.attachImage(watersurface);
    world.add(watersurf);
  }
  if (c == #00a2e8) {
    FBox h2o = new FBox(50, 50);
    h2o.setPosition(x*50, y*50);
    h2o.setStatic(true);
    h2o.setSensor(true);
    h2o.setName("H2O");
    h2o.attachImage(water);
    world.add(h2o);
  }
}

//===================================================================================

void makeLava(int x, int y, int c) {
  if (c == #ff7f27) {
    FBox lavasurf = new FBox(50, 50);
    lavasurf.setPosition(x*50, y*50);
    lavasurf.setStatic(true);
    lavasurf.setSensor(true);
    lavasurf.setName("lavatop");
    lavasurf.attachImage(lavasurface);
    world.add(lavasurf);
  }
  if (c == #ed1c24) {
    FBox lav = new FBox(50, 50);
    lav.setPosition(x*50, y*50);
    lav.setStatic(true);
    lav.setSensor(true);
    lav.setName("lava");
    lav.attachImage(lava);
    world.add(lav);
  }
} 

//===================================================================================

void makeSpike(int x, int y, color c) {

  if (c == #22b14c) {
    FBox spike = new FBox(50, 10);
    spike.setPosition(x*50, y*50 + 12);
    spike.setStatic(true);
    spike.setSensor(true);
    spike.setGrabbable(false);
    spike.setName("spikeI");
    spike.attachImage(spike1);
    world.add(spike);
  }
}

//===================================================================================

void makeSubject() {
  subject = new FBox(50, 69);
  subject.setPosition(600, 600);
  subject.setRotatable(false);
  subject.setGrabbable(false);
  subject.setName("player");
  world.add(subject);
}

//===================================================================================

void animateSubject() {
  float vx = subject.getVelocityX();
  currentAction = run;
  if (frameCount % 1 == 0) {
    frame++;
    if (frame >= currentAction.length) {
      frame = 0;
    }
  }
  if (vx < 5 && faceRight) {
    subject.attachImage(idleSubject);
  } else if (vx > -5 && !faceRight) {
    subject.attachImage(reverse(idleSubject));
  }
  if (faceRight && vx >= 5 && touchingGround()) {
    currentAction = run;
    subject.attachImage(currentAction[frame]);
  } else if (!faceRight && vx <= -5 && touchingGround()) {
    subject.attachImage(reverse(currentAction[frame]));
  }
  if (faceRight && !touchingGround()) {
    subject.attachImage(jumpSubject);
  } else if (!faceRight && !touchingGround()) {
    subject.attachImage(reverse(jumpSubject));
  }
}

//===================================================================================

void animateEnemies() {
  float pvx = pink.getVelocityX();
  if (frameCount % 30 == 0) {
    framePink++;
    if (framePink >= pinkmove.length) {
      framePink = 0;
    }
  }
  if (pvx < 0 && pink.getName().equals("badpink")) {
    pink.attachImage(pinkmove[framePink]);
  } else if (pvx > 0 && pink.getName().equals("badpink")) {
    pink.attachImage(reverse(pinkmove[framePink]));
  }
}

//===================================================================================

void makeEnemies(int x, int y, int c) {
  if (c == #ffaec9) {
    pink = new FBox(42, 28);
    pink.setName("badpink");
    pink.attachImage(pinkmove[frame]);
    pink.setPosition(x*50, y*50);
    pink.setDensity(0.1);
    pink.setFriction(-50);
    pink.setRestitution(0.7);
    pink.setRotatable(false);
    pink.setGrabbable(false);
    world.add(pink);
    oddBodies.add(pink);
  }
  if (c == #b5e61d) {
    green = new FBox(48, 146);
    green.setName("badgreen");
    green.attachImage(badgreen1);
    green.setPosition(x*50, y*50);
    green.setStatic(true);
    green.setSensor(true);
    green.setRotatable(false);
    green.setGrabbable(false);
    world.add(green);
  }
  if (c == #ff8040) {
    snail = new FBox(50, 29);
    snail.setName("badsnail");
    snail.attachImage(alivesnail1);
    snail.setPosition(x*50, y*50);
    snail.setDensity(3);
    snail.setFriction(50);
    snail.setRestitution(0.4);
    snail.setRotatable(false);
    snail.setGrabbable(false);
    world.add(snail);
    oddBodies.add(snail);
  }
}

//===================================================================================

void moveEnemies() {
  if (mode == PLAYING2) {
    pink.setVelocity(pinkSpeed*direction * 10, 0);
  }
}

//===================================================================================

void handleProjectiles(int x, int y, int c) {
  if (c == #b5e61d) {
    FBox greens = new FBox(30, 30);
    greens.setPosition(x*50, y*50 + 36);
    greens.setForce(10, -5);
    greens.setStatic(false);
    greens.setGrabbable(false);
    greens.setSensor(false);
    greens.setName("gspike");
    greens.attachImage(greensp);
    world.add(greens);
    oddBodies.add(greens);
  }
}

//===================================================================================

void moveSubject() {

  if (leftkey && mode == PLAYING2) {
    subject.adjustVelocity(-topSpeed, 0);
    faceRight = false;
    if (touchingWater()) {
      subject.adjustVelocity(-topSpeed + viscosity, 0);
      //subject.attachImage(moveLeft);
    }
  }

  if (rightkey && mode == PLAYING2) {
    subject.adjustVelocity(topSpeed, 0);
    faceRight = true;
    if (touchingWater()) {
      subject.adjustVelocity(topSpeed - viscosity, 0);
      //subject.attachImage(moveRight);
    }
  }

  if (upkey && mode == PLAYING2) {
    if (touchingGround()) {
      subject.setVelocity(subject.getVelocityX(), -1000);
      walkSound.pause();
      jumpSound.play();
      jumpSound.rewind();
    }
    if (touchingWater()) {
      subject.adjustVelocity(0, -70);
    }
    if (elevatorActivate()) {
      subject.adjustVelocity(0, -100);
    }
  }

  if (touchingWater()) {
    subject.adjustVelocity(0, -100);
  }
  float vx = subject.getVelocityX();
  float vy = subject.getVelocityY();
  if (vx <= -topSpeed) vx = -topSpeed;
  if (vx >= topSpeed) vx = topSpeed;
  subject.setVelocity(vx, vy);
  if (vx >= 30 && touchingGround()) {
    walkSound.play();
  } else if (vx <= -30 && touchingGround()) {
    walkSound.play();
  } else if (!touchingGround() || vx == 0) {
    walkSound.pause();
    walkSound.rewind();
  }
}

//===================================================================================

void makeKey(int x, int y, int c) {
  if (c == #1a1f60) {
    FBox bkey = new FBox(30, 30);
    bkey.setPosition(x*50, y*50);
    bkey.setStatic(true);
    bkey.setGrabbable(false);
    bkey.setSensor(true);
    bkey.setName("bkey");
    bkey.attachImage(bluekey);
    world.add(bkey);
    oddBodies.add(bkey);
  }
}

//===================================================================================

void makeCoin(int x, int y, color c) {
  if (c == #ffc90e) {
    FBox coin = new FBox(30, 30);
    coin.setPosition(x*50, y*50);
    coin.setStatic(true);
    coin.setGrabbable(false);
    coin.setSensor(true);
    coin.setName("gold");
    coin.attachImage(goldcoin);
    world.add(coin);
    oddBodies.add(coin);
  }
}

//===================================================================================

void makeSigns(int x, int y, int c) {
  if (c == #880015) {
    FBox leftie = new FBox(50, 50);
    leftie.setPosition(x*50, y*50);
    leftie.setStatic(true);
    leftie.setSensor(true);
    leftie.setGrabbable(false);
    leftie.attachImage(leftarrow);
    world.add(leftie);
  }

  if (c == #b97a57) {
    FBox rightie = new FBox(50, 50);
    rightie.setPosition(x*50, y*50);
    rightie.setStatic(true);
    rightie.setSensor(true);
    rightie.setGrabbable(false);
    rightie.attachImage(rightarrow);
    world.add(rightie);
  }
}
//===================================================================================

void makeTeleporter(int x, int y, int c) {
  if (c == #7092be) {
    FBox tele = new FBox(50, 50);
    tele.setPosition(x*50, y*50);
    tele.setStatic(true);
    tele.setSensor(true);
    tele.setName("teleporter");
    tele.attachImage(teleporter1);
    world.add(tele);
    oddBodies.add(tele);
  }
  if (c == #a9bdd8) {
    FBox texit = new FBox(80, 80);
    texit.setPosition(x*50, y*50);
    texit.setStatic(true);
    texit.setSensor(true);
    texit.setName("telexit");
    texit.attachImage(telexit1);
    world.add(texit);
    oddBodies.add(texit);
  }
}

//===================================================================================

void makeDoor1(int x, int y, int c) {
  if (c == #a349a4) {
    FBox bottom = new FBox(50, 50);
    bottom.setPosition(x*50, y*50);
    bottom.setStatic(true);
    bottom.setSensor(true);
    bottom.setName("bottomdoor");
    bottom.attachImage(bottomdoor);
    world.add(bottom);
    oddBodies.add(bottom);
  }
  if (c == #ae5eff) {
    FBox top = new FBox(50, 50);
    top.setPosition(x*50, y*50);
    top.setStatic(true);
    top.setSensor(true);
    top.setName("topdoor");
    top.attachImage(topdoor);
    world.add(top);
    oddBodies.add(top);
  }
}

//===================================================================================
void keyPressed() {
  if (mode == PLAYING2) {
    if (keyCode == UP) {
      //subject.setVelocity(subject.getVelocityX(), 0);
      upkey = true;
    }
    if (keyCode == LEFT) {
      subject.adjustVelocity(-topSpeed, 0);
      leftkey = true;
    }
    if (keyCode == RIGHT) {
      subject.adjustVelocity(topSpeed, 0);
      rightkey = true;
    }
  }
}

//===================================================================================

void keyReleased() {
  if (mode == PLAYING2) {
    if (keyCode == UP) {
      subject.adjustVelocity(0, 0);
      upkey = false;
    }
    if (keyCode == LEFT) {
      subject.adjustVelocity(-topSpeed, 0);
      leftkey = false;
    }
    if (keyCode == RIGHT) {
      subject.adjustVelocity(topSpeed, 0);
      rightkey = false;
    }
  }
}


