/* angular-accounting.js / v1.0.0 / (c) 2014 Joakim Millén / MIT Licence */

/* global define */

(function () {
	'use strict';

	function angularAccounting(angular, accounting) {

		/**
		 * @name angularAccounting
		 *
		 * @description
		 * angularAccounting module provides accounting.js functionality to angular.js apps.
		 */
		return angular.module('angularAccounting', [])

			/**
			 * @name angularAccounting.accounting
			 *
			 * @description
			 * accounting global as a constant
			 */
			.constant('accounting', {
					formatColumn 	: accounting.formatColumn,
					formatMoney 	: accounting.formatMoney,
					formatNumber 	: accounting.formatNumber,
					parse 			: accounting.parse,
					toFixed 		: accounting.toFixed,
					unformat 		: accounting.unformat
				})

			.constant( 'accountingSettings', accounting.settings );
	}

	if (typeof define === 'function' && define.amd) {
		define('angular-accounting', ['angular', 'accounting'], angularAccounting);
	} else {
		angularAccounting(angular, window.accounting);
	}
})();
