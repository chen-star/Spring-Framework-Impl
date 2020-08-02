package org.alex.aop;

import org.alex.aop.aspect.AspectInfo;
import org.alex.aop.mock.MockDefaultAspect1;
import org.alex.aop.mock.MockDefaultAspect2;
import org.alex.aop.mock.MockDefaultAspect3;
import org.alex.aop.mock.MockDefaultAspect4;
import org.alex.aop.mock.MockDefaultAspect5;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class AspectListExecutorTest {

    @DisplayName("AspectListExecutorTest: sortTest")
    @Test
    public void sortTest() {
        List<AspectInfo> aspectInfos = new ArrayList<>();
        aspectInfos.add(new AspectInfo(3, new MockDefaultAspect1(), null));
        aspectInfos.add(new AspectInfo(5, new MockDefaultAspect2(), null));
        aspectInfos.add(new AspectInfo(2, new MockDefaultAspect3(), null));
        aspectInfos.add(new AspectInfo(4, new MockDefaultAspect4(), null));
        aspectInfos.add(new AspectInfo(1, new MockDefaultAspect5(), null));

        AspectListExecutor executor = new AspectListExecutor(AspectListExecutorTest.class, aspectInfos);
        List<AspectInfo> sortedAspectInfos = executor.getSortedAspectInfoList();
        for(AspectInfo aspectInfo : sortedAspectInfos) {
            System.out.println(aspectInfo.getAspectObject().getClass());
        }
        Assertions.assertEquals(MockDefaultAspect1.class, sortedAspectInfos.get(2).getAspectObject().getClass());
    }
}