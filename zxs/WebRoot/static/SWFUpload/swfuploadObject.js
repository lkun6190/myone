/**
 * Created by Administrator on 2016-12-27.
 */
var swfu = null;

(function($) {

    var settings_object = null;
    $.fn.SwfUploadJs = function(options) {
        var id=$(this).attr("id");
        var defaults = {
            post_params : {},
// File Upload Settings
            file_size_limit : "100 MB",
            file_types : "*.*",
            file_types_description : "请选择文件",
            file_upload_limit : "0",    // Zero means unlimited
// Button settings
            button_placeholder_id : id,

            button_width: 160,
            button_height: 30,
            button_image_url: ctxStatic+ "/SWFUpload/images/input.png",
            button_text : '<span class="button" style=" background-color:#3daae9;">请选择文件 (最大100MB)</span>',
            button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; line-height:25; background-color:#3daae9; } .buttonSmall { font-size: 10pt; }',
            button_text_top_padding: 4,
            button_text_left_padding: 5,
            button_action:SWFUpload.BUTTON_ACTION.SELECT_FILE,

// Flash Settings
            flash_url : ctxStatic+"/SWFUpload/swfupload.swf", // Relative to this file

// Debug Settings
            debug: false,
            upload_progress_handler : uploadProgress,
            upload_error_handler : uploadError,
            upload_success_handler : uploadSuccess,
            upload_complete_handler : uploadComplete,
            show_message:null//flag (0 normal 1 error 2 finished),message
        };


        var options = $.extend(defaults, options);
        settings_object = {
            upload_url: options.upload_url,
            post_params: options.post_params,
            use_query_string:true,
// File Upload Settings
            file_size_limit : "100 MB",
            file_types : "*.*",
            file_upload_limit : "0",
            file_types_description :"All Files",
// Button settings
            button_placeholder_id : "spanButtonPlaceholder",

            button_width: options.button_width,
            button_height: options.button_height,
            button_text : options.button_text,
            button_text_style: options.button_text_style,
            button_image_url:options.button_image_url,
            button_text_top_padding: options.button_text_top_padding,
            button_text_left_padding: options.button_text_left_padding,

// Flash Settings
            flash_url : ctxStatic+"/SWFUpload/swfupload.swf" , // Relative to this file

// Debug Settings
            debug: options.debug,

            //EventHandler

            file_queue_error_handler : fileQueueError,
            file_dialog_complete_handler : fileDialogComplete,
            file_queued_handler : fileQueued,
            upload_progress_handler : uploadProgress,
            upload_error_handler : uploadError,
            upload_success_handler : options.upload_success_handler,
            upload_complete_handler : options.upload_complete_handler,

            debug_handler : options.debug_handler,
            show_message:options.show_message,
            custom_settings : {
                upload_target : "divFileProgressContainer",
                upload_complete_callback:options.upload_complete_callback
            }

        };
        swfu = new SWFUpload(settings_object);

        var myFlash = (function(){
            if(typeof window.ActiveXObject != "undefined"){
                try {
                    var v = new ActiveXObject("ShockwaveFlash.ShockwaveFlash");
                }catch (e){

                }
                return v;
            }else{
                return navigator.plugins['Shockwave Flash'];
            }
        })();


        if(typeof myFlash  == "undefined")  {
            alert("本机未安装Flash视频播放控件，请在百度软件中心下载安装，否则无法上传文件！");
            window.location = "http://rj.baidu.com/soft/detail/17153.html";
        }

    }

    $.fn.SetSwfUploadParams=function(param){
        if(swfu==null){
            swfu = new SWFUpload(settings_object);
        }

        for(var p in param){
            swfu.addPostParam(p,param[p]);
        }
    }



})(jQuery);

function startUploadFile(){
    swfu.startUpload();
}