$(document).ready(function() {
    $('.js-modal').modal();
    loadAnnouncements();
});

function loadAnnouncements() {
    $.ajax({
        type: "POST",
        url: "announcement?action=get-all-announcement"
    }).done(function(data) {
        if (data.length != 0) {
            let rsl = drawAnnouncements(data);
            $('.js-user-announcement').html(rsl);
        }
    }).fail(function(err) {
        let instance = M.Modal.getInstance($(".js-modal"));
        instance.open();
        showModalError("Ошибка на стороне сервера, перезагрузите страницу");
    });
}