package com.yellowfinbi.api.sections.images;
import com.yellowfinbi.api.common.IYFServerDetails;
import com.yellowfinbi.api.transport.YFTransport;
public final class YFImagesApi {
    private YFImagesApi(){}
    public static IYFImageModel getImage(IYFServerDetails server, String accessToken, long imageId, String thumbSize) {
        String endpoint = "api/images/" + imageId;
        if (thumbSize != null && !thumbSize.isBlank()) endpoint += "?thumbSize=" + thumbSize;
        YFTransport transport = new YFTransport(YFTransport.HttpVerb.GET, server, endpoint, accessToken, null, "image/*");
        YFImageModel img = new YFImageModel();
        img.assign(transport.executeForBytes());
        return img;
    }
}
