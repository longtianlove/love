package longtianlove.myapplication.tinker;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by 58 on 2016/12/20.
 */

public class SampleApplication extends TinkerApplication {
    public SampleApplication(){
        super(ShareConstants.TINKER_ENABLE_ALL,"longtianlove.myapplication.LongApplication");
    }
}
