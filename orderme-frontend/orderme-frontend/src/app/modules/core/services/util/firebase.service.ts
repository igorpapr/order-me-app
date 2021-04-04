import {Injectable} from '@angular/core';
import {AngularFireStorage} from "@angular/fire/storage";

@Injectable({
  providedIn: 'root'
})
export class FirebaseService {

  constructor(private fireStorage: AngularFireStorage) { }

  /**
   * Returns task of uploading to firebase which can be observed and upload percentage can be taken:
   * Ex:
   *     // observe percentage changes
   *     this.uploadPercent = task.percentageChanges();
   *     // get notified when the download URL is available
   *     task.snapshotChanges().pipe(
   *     finalize(() => this.downloadURL = fileRef.getDownloadURL() )
   *     ).subscribe()
   * @param file - the file to be downloaded
   * @param filePath - the filepath on which to download the file
   */
  uploadFile(file: any, filePath: string) {
    return this.fireStorage.upload(filePath, file);
  }

  getFileReferenceByFilePath(filePath: string) {
    return this.fireStorage.ref(filePath);
  }
}
