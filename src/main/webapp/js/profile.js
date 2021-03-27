$(document).ready(function() {
    $('.js-modal').modal();
    loadAnnouncements();
});

function loadAnnouncements() {
    let userId = getUrlParam("id");
    if (userId) {
        $.ajax({
            type: "POST",
            url: "announcement?action=get-user-announcement&id=" + userId
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
    } else {
        let instance = M.Modal.getInstance($(".js-modal"));
        instance.open();
        showModalError("Неверный запрос.");
    }
}