package juvi

import java.sql.Connection

trait ConnectionProvider
{
  def getConnection : Connection
}