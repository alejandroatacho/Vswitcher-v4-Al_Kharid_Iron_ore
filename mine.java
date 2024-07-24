// ID of the iron ore & Depleted Iron Ore
int iron_ore = 440;
GameObject depletedObj_1 = v.getGameObject().findNearest(11390);
GameObject depletedObj_2 = v.getGameObject().findNearest(11391);
// Gems Id
int ruby = 1619;
int emerald = 1621;
int sapphire = 1623;
int diamond = 1617;
//gem bag variables story
int gem_bag = 24481;
int gem_bag_closed = 12020;
int gem_bag_slot = v.getInventory().slot(gem_bag);            
int gem_bag_closed_slot = v.getInventory().slot(gem_bag_closed);            
WorldPoint wp1 = new WorldPoint(3295, 3310, 0);

private void openGemBag() {
    if (v.getInventory().hasIn(gem_bag_closed)) {
        v.invoke("Open","<col=ff9040>Gem bag</col>",3,57,gem_bag_closed_slot,9764864,false);
    }
}

private void dropGems() {
    if (v.getInventory().hasIn(gem_bag))
    {
      v.invoke("Fill","<col=ff9040>Open gem bag</col>",2,57,gem_bag_slot,9764864,false);
    }
    else {
         v.getInventory().drop(ruby, sapphire, diamond, emerald);   
    }
    
}

private void handleRunning() {
    if (client.getEnergy() == 2000) {
        v.getWalking().turnRunningOn();
    }
}

private void mineIronOre() {
    WorldPoint currentLocation = client.getLocalPlayer().getWorldLocation();

    if (!currentLocation.equals(wp1)) { 

        handleRunning();
        v.getWalking().walk(wp1);
    } else {


        if (v.getInventory().inventoryFull()) {
            v.getInventory().drop(iron_ore);
            if (v.getInventory().hasIn(ruby) || v.getInventory().hasIn(emerald) || v.getInventory().hasIn(diamond) || v.getInventory().hasIn(sapphire)) {
                log.info("We found gems");
                openGemBag();
                dropGems();
            }
        } else {
            if (!v.getLocalPlayer().hasAnimation(627) && !v.getWalking().isMoving() && (v.getWalking().nearEntity(Entity.GAME, 11364, 1) || v.getWalking().nearEntity(Entity.GAME, 11365, 1))) {
                v.getMining().mine(11364, 11365);
            }
            else if ((depletedObj_1 == null) && (depletedObj_2 == null)) {
                //mine the 3rd rock next to you
                    v.invoke("Mine","<col=ffff>Iron rocks",11364,3,55,55,false);
            }
            
        }
    }
}

mineIronOre();