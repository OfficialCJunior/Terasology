/*
 * Copyright 2013 Benjamin Glatzel <benjamin.glatzel@me.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.terasology.network;

import org.junit.Before;
import org.junit.Test;
import org.terasology.entitySystem.PersistableEntityManager;
import org.terasology.entitySystem.metadata.EntitySystemLibrary;
import org.terasology.game.ComponentSystemManager;
import org.terasology.game.CoreRegistry;
import org.terasology.game.Timer;
import org.terasology.game.bootstrap.EntitySystemBuilder;
import org.terasology.logic.mod.ModManager;

import static org.mockito.Mockito.mock;

/**
 * @author Immortius
 */
public class TestNetwork {

    @Before
    public void setup() {
        CoreRegistry.put(ComponentSystemManager.class, new ComponentSystemManager());
    }

    @Test
    public void testNetwork() throws InterruptedException {
        PersistableEntityManager entityManager = new EntitySystemBuilder().build(new ModManager());
        Timer timer = mock(Timer.class);
        NetworkSystem server = new NetworkSystemImpl(timer);
        server.connectToEntitySystem(entityManager, CoreRegistry.get(EntitySystemLibrary.class), null);
        server.host(7777);

        Thread.sleep(500);

        NetworkSystem client = new NetworkSystemImpl(timer);
        client.join("localhost", 7777);

        Thread.sleep(500);

        server.shutdown();
        client.shutdown();
    }
}
