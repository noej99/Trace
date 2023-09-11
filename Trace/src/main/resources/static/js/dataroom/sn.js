$(function() {
	$('#summernote').summernote({
		width: 800,
		height: 300,
		focus: true,
		lang: "ko-KR",
		disableDragAndDrop: true,
		toolbar: [
			// [groupName, [list of button]]
			['style', ['bold', 'italic', 'underline', 'strikethrough', 'clear']],
			['color', ['forecolor', 'color']],
			['table', ['table']],
			['para', ['ul', 'ol', 'paragraph']],
			['insert', ['picture']],
		],
		popover :[],
		callbacks : { 
			onImageUpload: function (files, editor, welEditable) {
				//alert(files[0].name);
				if(isPhoto(files[0].name)){
					alert(".png, .gif, .jpg, .webp이미지만 올릴 수 있습니다.");
					$('.note-group-select-from-files').find("input").val("");
				} else {
					//alert("ㅎㅇ");
	                uploadSummernoteImageFile(files[0], this);
				}
            }
		}
	});
	$('.note-group-select-from-files').find("input").removeAttr("multiple");
	$('#summernote').summernote('pasteHTML', $("#summernote").attr("value"));
	$('.note-editable').html($("#summernote").attr("value"));
	$('#summernote').summernote('fontName', '맑은 고딕');
	$('#summernote').summernote('fontSize', '12');
	
});

function uploadSummernoteImageFile(file, el) {
	data = new FormData()
    data.append('imgFile', file)
    $.ajax({
        data: data,
        type: 'POST',
        url: 'dataroom.uploadImage',
        contentType: false,
        enctype: 'multipart/form-data',
        processData: false,
        success: function (data) {
            $(el).summernote(
                /*'editor.insertImage',
                data.url,*/
                'editor.pasteHTML',
                data.url,
                //alert(data.url)
            )
            $('.note-group-select-from-files').find("input").val("");
        },
    })
}
