package org.easycloud.las.analyzer.io;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;

/**
 * Created with IntelliJ IDEA.
 * User: ibntab
 * Date: 13-6-25
 * Time: 下午4:49
 */
public class TmpFileExcludePathFilter implements PathFilter {

    private final String end = ".tmp";
    @Override
    public boolean accept(Path path) {
        return !path.toString().endsWith(end);
    }
}
