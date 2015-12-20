//***********************************
//surfacetheme.com
//Project Name: IUMS
//Version:01
//***********************************
$(function () {
    // InsightM8  Contact

    $(".contact-input").focus(function () {
        $(this).parent().addClass("is-active is-completed");
    });
    $(".contact-input").focusout(function () {
        if ($(this).val() === "") {
            $(this).parent().removeClass("is-completed");
        }
        $(this).parent().removeClass("is-active");
    });
    var animeTime = 300;
    //getFixed
    $("#getFixed").sticky({ topSpacing: 0 });
});
