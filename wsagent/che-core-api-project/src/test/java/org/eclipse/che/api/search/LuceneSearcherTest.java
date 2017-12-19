/*
 * Copyright (c) 2012-2017 Red Hat, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.che.api.search;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.che.api.fs.server.PathTransformer;
import org.eclipse.che.api.search.server.SearchResult;
import org.eclipse.che.api.search.server.impl.LuceneSearcher;
import org.eclipse.che.api.search.server.impl.QueryExpression;
import org.eclipse.che.api.search.server.impl.SearchResultEntry;
import org.eclipse.che.commons.lang.IoUtil;
import org.eclipse.che.commons.lang.NameGenerator;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LuceneSearcherTest {
  private static final String[] TEST_CONTENT = {
    "Apollo set several major human spaceflight milestones",
    "Maybe you should think twice",
    "To be or not to be beeeee lambergeeene",
    "In early 1961, direct ascent was generally the mission mode in favor at NASA",
    "Time to think"
  };

  private File indexDirectory;
  private LuceneSearcher searcher;
  private Path tempDirectory;

  private static Pattern fileExtnPtrn = Pattern.compile("([^\\s]+(\\.(?i)(txt|doc|csv|pdf))$)");

  public static boolean validateFileExtn(String userName){

    Matcher mtch = fileExtnPtrn.matcher(userName);
    if(mtch.matches()){
      return true;
    }
    return false;
  }

  @BeforeMethod
  public void setUp() throws Exception {

    File targetDir =
        new File(Thread.currentThread().getContextClassLoader().getResource(".").getPath())
            .getParentFile();
    indexDirectory = new File(targetDir, NameGenerator.generate("index-", 4));
    assertTrue(indexDirectory.mkdir());
  }

  @AfterMethod
  public void tearDown() throws Exception {
    //    searcher.close();
    IoUtil.deleteRecursive(indexDirectory);
  }

  @Test
  public void initializesIndexForExistedFiles() throws Exception {
    tempDirectory = Files.createTempDirectory("testsSearch");
    Files.createFile(Paths.get(tempDirectory.toString(), "xxx.txt"));
    Files.createFile(Paths.get(tempDirectory.toString(), "zzz.java"));
    Files.createFile(Paths.get(tempDirectory.toString(), "aaa.yaml"));
    Files.createFile(Paths.get(tempDirectory.toString(), "bbb.java"));
    Files.createFile(Paths.get(tempDirectory.toString(), "zzz.txt"));
    searcher =
        new LuceneSearcher(
            Collections.emptySet(),
            indexDirectory,
            tempDirectory.toFile(),
            new PathTransformer() {
              @Override
              public Path transform(String wsPath) {
                return Paths.get(wsPath);
              }

              @Override
              public String transform(Path fsPath) {
                return fsPath.toString();
              }
            });
    searcher.initialize();
    Thread.sleep(1000);
    QueryExpression query = new QueryExpression().setName(".*.(xml|txt|json|md|java|yaml|yml)");
    SearchResult search = searcher.search(query);
    List<SearchResultEntry> results = search.getResults();
    assertEquals(results.size(), 5);
    //TODO: result must be sorted by file extension and file name
    results.forEach(searchResultEntry -> {
      String filePath = searchResultEntry.getFilePath();
      System.out.println(filePath);
    });


  }


}
