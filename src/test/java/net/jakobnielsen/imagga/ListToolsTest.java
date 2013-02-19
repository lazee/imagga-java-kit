/*
 * Copyright 2013 Jakob Vad Nielsen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.jakobnielsen.imagga;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class ListToolsTest {

    @Test
    public void testImplode() throws Exception {
        Assert.assertEquals("1,2,3,4", ListTools.implode(Arrays.asList("1", "2", "3", "4"), ","));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testImplodeNull() throws Exception {
        Assert.assertNull(ListTools.implode(null, null));
    }

}
