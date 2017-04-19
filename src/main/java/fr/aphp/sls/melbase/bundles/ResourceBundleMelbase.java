package fr.aphp.sls.melbase.bundles;

/**
 * Classe permettant de générer un bundle de paramétrage pour 
 * obtenir les adresses des différentes applications en 
 * utilisant le fichier de propriétés. L'emplacement de ce fichier 
 * est défini via une variable JNDI.
 * 
 * @author Mathieu BAR%THELEMY.
 *
 */
public interface ResourceBundleMelbase {

	String getTkAppName();

	String getHostName();

	String getInclusionAppName();

	String getClinicaAppName();

}
