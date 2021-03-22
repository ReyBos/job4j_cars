$('.js-do-logout').click(function () {
    $.ajax({
        type: "POST",
        url: "user?action=logout",
    }).done(function() {
        window.location.reload();
    })
});