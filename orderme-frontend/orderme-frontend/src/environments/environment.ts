// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  apiUrl_v1: `/api/v1/`,
    //`http://localhost:8080/api/v1/`,
  firebaseConfig: {
    apiKey: "AIzaSyBV2IaH7n7jN9xnFFFXixq7JvoKFA0HZ-c",
    authDomain: "ordermeimagestorage.firebaseapp.com",
    projectId: "ordermeimagestorage",
    storageBucket: "ordermeimagestorage.appspot.com",
    messagingSenderId: "181558629457",
    appId: "1:181558629457:web:4435773c3bd6c5dbaa9470"
  }
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
