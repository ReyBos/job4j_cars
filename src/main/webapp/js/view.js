$('.js-is_sold').change(function () {
    let isSold = $(".js-is_sold").prop('checked');
    let announcementId = $(".js-is_sold").data('announcement_id');
    $('.js-is_sold').prop("disabled", true);
    let data = {
        "isSold": isSold,
        "announcementId": announcementId
    };
    $.ajax({
        type: "POST",
        url: "announcement?action=update",
        contentType: "application/json",
        data: JSON.stringify(data),
    }).done(function(response) {
        $('.js-is_sold').prop("disabled", false);
    }).fail(function(err) {
        showModalError("Ошибка при обновлении объявления, перезагрузите страницу или повторите запрос позднее.");
    });
});